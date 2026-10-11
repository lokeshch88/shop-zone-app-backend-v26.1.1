# 💻 Frontend Models & Interfaces

## TypeScript Interfaces for React/Angular

### Product Model

```typescript
// models/product.model.ts

export interface ProductImage {
  id: number;
  productId: number;
  imageUrl: string;
  fileName: string;
  primaryImage: boolean;
  displayOrder: number;
}

export interface MerchantReply {
  id: number;
  reviewId: number;
  merchantId: number;
  merchantName: string;
  replyText: string;
  replyType: 'RESOLUTION' | 'CLARIFICATION' | 'THANK_YOU' | 'APOLOGY';
  createdAt: string;
  updatedAt: string;
}

export interface ReviewPhoto {
  id: number;
  reviewId: number;
  photoBase64: string;
  photoUrl: string;
  displayOrder: number;
  uploadedAt: string;
}

export interface ProductReview {
  id: number;
  productId: number;
  userId: number;
  username: string;
  rating: number;  // 1-5
  title: string;
  content: string;
  isVerifiedPurchase: boolean;
  isApproved: boolean;
  createdAt: string;
  updatedAt: string;
  photos: ReviewPhoto[];
  replies: MerchantReply[];
}

export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  quantityInStock: number;
  brand: string;
  sku: string;
  weight: number;
  dimensions: string;
  createdAt: string;
  updatedAt: string;
  isActive: string;
  imageUrl: string;
  manufacturer: string;
  rating: number;
  mrp: number;
  discountPrice: number;
  discountPercentage: number;
  categoryId: number;
  comparePrice: number;
  imageBase64: string;
  images: ProductImage[];
  averageRating: number;      // ⭐ NEW
  totalReviews: number;        // ⭐ NEW
  reviews: ProductReview[];    // ⭐ NEW
}

export interface PaginatedResponse<T> {
  totalElements: number;
  totalPages: number;
  currentPage: number;
  reviews: T[];
}

export interface RatingDistribution {
  '1 Star': number;
  '2 Star': number;
  '3 Star': number;
  '4 Star': number;
  '5 Star': number;
}

export interface AverageRatingResponse {
  averageRating: number;
}
```

---

## React Component Examples

### Display Product Rating

```typescript
// components/ProductRating.tsx

import React from 'react';
import { Product } from '../models/product.model';

interface ProductRatingProps {
  product: Product;
}

export const ProductRating: React.FC<ProductRatingProps> = ({ product }) => {
  const renderStars = (rating: number) => {
    return Array(5)
      .fill(0)
      .map((_, i) => (
        <span key={i} className={i < Math.round(rating) ? 'star filled' : 'star'}>
          ⭐
        </span>
      ));
  };

  return (
    <div className="product-rating">
      <div className="stars">
        {renderStars(product.averageRating)}
      </div>
      <span className="rating-value">
        {product.averageRating.toFixed(2)} / 5.0
      </span>
      <span className="review-count">
        ({product.totalReviews} reviews)
      </span>
    </div>
  );
};
```

---

### Display Reviews List

```typescript
// components/ReviewsList.tsx

import React, { useState, useEffect } from 'react';
import { ProductReview, PaginatedResponse } from '../models/product.model';
import { reviewService } from '../services/review.service';

interface ReviewsListProps {
  productId: number;
}

export const ReviewsList: React.FC<ReviewsListProps> = ({ productId }) => {
  const [reviews, setReviews] = useState<ProductReview[]>([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadReviews();
  }, [page]);

  const loadReviews = async () => {
    setLoading(true);
    try {
      const response = await reviewService.getProductReviews(productId, page, 20);
      setReviews(response.reviews);
      setTotalPages(response.totalPages);
    } catch (error) {
      console.error('Failed to load reviews:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="reviews-list">
      {loading && <p>Loading reviews...</p>}
      
      {reviews.map(review => (
        <div key={review.id} className="review-item">
          <div className="review-header">
            <div className="reviewer-info">
              <h4>{review.username}</h4>
              <p className="review-date">
                {new Date(review.createdAt).toLocaleDateString()}
              </p>
            </div>
            <div className="review-rating">
              {Array(5).fill(0).map((_, i) => (
                <span key={i}>
                  {i < review.rating ? '⭐' : '☆'}
                </span>
              ))}
              <span className="rating-value">{review.rating}/5</span>
            </div>
          </div>

          <h5 className="review-title">{review.title}</h5>
          <p className="review-content">{review.content}</p>

          {review.isVerifiedPurchase && (
            <span className="badge verified">✓ Verified Purchase</span>
          )}

          {/* Review Photos */}
          {review.photos.length > 0 && (
            <div className="review-photos">
              {review.photos.slice(0, 5).map(photo => (
                <img
                  key={photo.id}
                  src={`data:image/jpeg;base64,${photo.photoBase64}`}
                  alt={`Review photo ${photo.displayOrder}`}
                  className="review-photo"
                />
              ))}
            </div>
          )}

          {/* Merchant Reply */}
          {review.replies.length > 0 && (
            <div className="merchant-reply">
              <div className="merchant-header">
                <h5>{review.replies[0].merchantName}</h5>
                <span className="reply-type">{review.replies[0].replyType}</span>
              </div>
              <p className="reply-text">{review.replies[0].replyText}</p>
              <small className="reply-date">
                {new Date(review.replies[0].createdAt).toLocaleDateString()}
              </small>
            </div>
          )}
        </div>
      ))}

      {/* Pagination */}
      <div className="pagination">
        <button disabled={page === 0} onClick={() => setPage(page - 1)}>
          Previous
        </button>
        <span>Page {page + 1} of {totalPages}</span>
        <button disabled={page >= totalPages - 1} onClick={() => setPage(page + 1)}>
          Next
        </button>
      </div>
    </div>
  );
};
```

---

### Rating Distribution Chart

```typescript
// components/RatingDistribution.tsx

import React, { useEffect, useState } from 'react';
import { RatingDistribution } from '../models/product.model';
import { reviewService } from '../services/review.service';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

interface RatingDistributionProps {
  productId: number;
}

export const RatingDistributionComponent: React.FC<RatingDistributionProps> = ({ productId }) => {
  const [distribution, setDistribution] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadDistribution();
  }, []);

  const loadDistribution = async () => {
    setLoading(true);
    try {
      const data = await reviewService.getRatingDistribution(productId);
      const chartData = [
        { name: '5 Stars', count: data['5 Star'] },
        { name: '4 Stars', count: data['4 Star'] },
        { name: '3 Stars', count: data['3 Star'] },
        { name: '2 Stars', count: data['2 Star'] },
        { name: '1 Star', count: data['1 Star'] },
      ];
      setDistribution(chartData);
    } catch (error) {
      console.error('Failed to load rating distribution:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <p>Loading...</p>;

  return (
    <div className="rating-distribution">
      <h3>Rating Distribution</h3>
      <ResponsiveContainer width="100%" height={300}>
        <BarChart data={distribution}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Bar dataKey="count" fill="#8884d8" />
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
};
```

---

### Add Review Form

```typescript
// components/AddReviewForm.tsx

import React, { useState } from 'react';
import { reviewService } from '../services/review.service';

interface AddReviewFormProps {
  productId: number;
  onReviewAdded: () => void;
}

export const AddReviewForm: React.FC<AddReviewFormProps> = ({ productId, onReviewAdded }) => {
  const [rating, setRating] = useState(5);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    setSuccess('');

    try {
      const response = await reviewService.createReview(productId, {
        rating,
        title,
        content,
      });

      setSuccess('Review submitted successfully!');
      setTitle('');
      setContent('');
      setRating(5);
      
      // Refresh reviews list
      onReviewAdded();
      
      // Show upload photos option
      // (Implement file upload for review photos)
      
    } catch (err: any) {
      setError(err.message || 'Failed to submit review');
    } finally {
      setLoading(false);
    }
  };

  return (
    <form className="add-review-form" onSubmit={handleSubmit}>
      <h3>Add Your Review</h3>

      {error && <div className="error-message">{error}</div>}
      {success && <div className="success-message">{success}</div>}

      <div className="form-group">
        <label>Rating *</label>
        <div className="rating-input">
          {[1, 2, 3, 4, 5].map(star => (
            <button
              key={star}
              type="button"
              className={`star ${rating >= star ? 'selected' : ''}`}
              onClick={() => setRating(star)}
            >
              {rating >= star ? '⭐' : '☆'}
            </button>
          ))}
          <span className="rating-value">{rating}/5</span>
        </div>
      </div>

      <div className="form-group">
        <label htmlFor="title">Title (Optional)</label>
        <input
          id="title"
          type="text"
          value={title}
          onChange={e => setTitle(e.target.value)}
          placeholder="Give your review a title"
          maxLength={500}
        />
      </div>

      <div className="form-group">
        <label htmlFor="content">Your Review (Optional)</label>
        <textarea
          id="content"
          value={content}
          onChange={e => setContent(e.target.value)}
          placeholder="Share your experience with this product"
          rows={5}
          maxLength={3000}
        />
        <small>{content.length}/3000 characters</small>
      </div>

      <button type="submit" disabled={loading} className="btn-primary">
        {loading ? 'Submitting...' : 'Submit Review'}
      </button>
    </form>
  );
};
```

---

## Service Layer

### Review Service

```typescript
// services/review.service.ts

import axios from 'axios';
import { ProductReview, PaginatedResponse, RatingDistribution, AverageRatingResponse } from '../models/product.model';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080';

export const reviewService = {
  // Create review
  createReview(productId: number, data: { rating: number; title?: string; content?: string }) {
    const params = new URLSearchParams();
    params.append('rating', data.rating.toString());
    if (data.title) params.append('title', data.title);
    if (data.content) params.append('content', data.content);

    return axios.post(
      `${API_BASE_URL}/api/reviews/products/${productId}`,
      params,
      {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      }
    );
  },

  // Get reviews for product
  getProductReviews(productId: number, page: number = 0, size: number = 20): Promise<PaginatedResponse<ProductReview>> {
    return axios.get(
      `${API_BASE_URL}/api/reviews/products/${productId}?page=${page}&size=${size}`
    ).then(res => res.data);
  },

  // Get reviews by rating
  getReviewsByRating(productId: number, rating: number, page: number = 0): Promise<any> {
    return axios.get(
      `${API_BASE_URL}/api/reviews/products/${productId}/rating/${rating}?page=${page}`
    ).then(res => res.data);
  },

  // Get average rating
  getAverageRating(productId: number): Promise<AverageRatingResponse> {
    return axios.get(
      `${API_BASE_URL}/api/reviews/products/${productId}/average-rating`
    ).then(res => res.data);
  },

  // Get rating distribution
  getRatingDistribution(productId: number): Promise<RatingDistribution> {
    return axios.get(
      `${API_BASE_URL}/api/reviews/products/${productId}/rating-distribution`
    ).then(res => res.data);
  },

  // Add photo to review
  addPhotoToReview(reviewId: number, file: File, displayOrder: number = 1) {
    const formData = new FormData();
    formData.append('photo', file);
    formData.append('displayOrder', displayOrder.toString());

    return axios.post(
      `${API_BASE_URL}/api/reviews/${reviewId}/photos`,
      formData,
      {
        headers: { 'Content-Type': 'multipart/form-data' },
      }
    ).then(res => res.data);
  },

  // Get replies for review
  getReplies(reviewId: number) {
    return axios.get(
      `${API_BASE_URL}/api/reviews/${reviewId}/replies`
    ).then(res => res.data);
  },

  // Update review
  updateReview(reviewId: number, data: { rating?: number; title?: string; content?: string }) {
    const params = new URLSearchParams();
    if (data.rating) params.append('rating', data.rating.toString());
    if (data.title) params.append('title', data.title);
    if (data.content) params.append('content', data.content);

    return axios.put(
      `${API_BASE_URL}/api/reviews/${reviewId}`,
      params,
      {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      }
    ).then(res => res.data);
  },

  // Delete review
  deleteReview(reviewId: number) {
    return axios.delete(`${API_BASE_URL}/api/reviews/${reviewId}`);
  },
};
```

---

## Styling (CSS/SCSS)

```scss
// styles/reviews.scss

.product-rating {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 20px;

  .stars {
    font-size: 20px;
    display: flex;
    gap: 2px;
  }

  .star {
    color: #ffc107;
  }

  .rating-value {
    font-weight: bold;
    font-size: 16px;
  }

  .review-count {
    color: #6c757d;
  }
}

.reviews-list {
  max-width: 800px;

  .review-item {
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    padding: 20px;
    margin-bottom: 20px;
    background: #f9f9f9;

    .review-header {
      display: flex;
      justify-content: space-between;
      margin-bottom: 15px;
      align-items: flex-start;

      .reviewer-info h4 {
        margin: 0;
        font-size: 16px;
      }

      .review-date {
        color: #6c757d;
        font-size: 12px;
        margin: 5px 0 0 0;
      }

      .review-rating {
        display: flex;
        align-items: center;
        gap: 5px;
      }
    }

    .review-title {
      margin: 0 0 10px 0;
      font-size: 16px;
      font-weight: 600;
    }

    .review-content {
      color: #333;
      line-height: 1.5;
      margin-bottom: 10px;
    }

    .badge {
      display: inline-block;
      background: #28a745;
      color: white;
      padding: 4px 8px;
      border-radius: 4px;
      font-size: 12px;
      margin-right: 10px;
    }

    .review-photos {
      display: flex;
      gap: 10px;
      margin-top: 15px;
      flex-wrap: wrap;

      .review-photo {
        width: 100px;
        height: 100px;
        object-fit: cover;
        border-radius: 4px;
        cursor: pointer;

        &:hover {
          opacity: 0.8;
        }
      }
    }

    .merchant-reply {
      background: white;
      border-left: 4px solid #007bff;
      padding: 15px;
      margin-top: 15px;
      border-radius: 4px;

      .merchant-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 10px;

        h5 {
          margin: 0;
          font-size: 14px;
        }

        .reply-type {
          background: #e7f3ff;
          color: #007bff;
          padding: 2px 8px;
          border-radius: 4px;
          font-size: 11px;
          font-weight: 600;
        }
      }

      .reply-text {
        color: #333;
        margin: 10px 0 5px 0;
        font-size: 14px;
      }

      .reply-date {
        color: #6c757d;
        font-size: 11px;
      }
    }
  }
}

.rating-distribution {
  background: white;
  padding: 20px;
  border-radius: 8px;
  border: 1px solid #e0e0e0;
}

.add-review-form {
  background: white;
  padding: 20px;
  border-radius: 8px;
  border: 1px solid #e0e0e0;
  max-width: 600px;

  .form-group {
    margin-bottom: 20px;

    label {
      display: block;
      margin-bottom: 8px;
      font-weight: 600;
      font-size: 14px;
    }

    input,
    textarea {
      width: 100%;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 4px;
      font-family: inherit;
      font-size: 14px;

      &:focus {
        outline: none;
        border-color: #007bff;
      }
    }

    small {
      display: block;
      color: #6c757d;
      margin-top: 5px;
      font-size: 12px;
    }
  }

  .rating-input {
    display: flex;
    align-items: center;
    gap: 5px;

    .star {
      background: none;
      border: none;
      font-size: 30px;
      cursor: pointer;
      padding: 0;
      color: #ccc;
      transition: color 0.2s;

      &.selected {
        color: #ffc107;
      }
    }

    .rating-value {
      margin-left: 10px;
      font-weight: 600;
    }
  }

  .error-message {
    background: #f8d7da;
    color: #721c24;
    padding: 12px;
    border-radius: 4px;
    margin-bottom: 15px;
    border: 1px solid #f5c6cb;
  }

  .success-message {
    background: #d4edda;
    color: #155724;
    padding: 12px;
    border-radius: 4px;
    margin-bottom: 15px;
    border: 1px solid #c3e6cb;
  }

  .btn-primary {
    background: #007bff;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 4px;
    font-size: 14px;
    font-weight: 600;
    cursor: pointer;
    width: 100%;

    &:hover {
      background: #0056b3;
    }

    &:disabled {
      background: #6c757d;
      cursor: not-allowed;
    }
  }
}
```

---

## Usage Example

```typescript
// pages/ProductDetailPage.tsx

import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Product } from '../models/product.model';
import { ProductRating } from '../components/ProductRating';
import { ReviewsList } from '../components/ReviewsList';
import { RatingDistributionComponent } from '../components/RatingDistribution';
import { AddReviewForm } from '../components/AddReviewForm';
import { productService } from '../services/product.service';

export const ProductDetailPage: React.FC = () => {
  const { productId } = useParams<{ productId: string }>();
  const [product, setProduct] = useState<Product | null>(null);
  const [refreshReviews, setRefreshReviews] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadProduct();
  }, []);

  const loadProduct = async () => {
    try {
      const data = await productService.getProduct(Number(productId));
      setProduct(data);
    } catch (error) {
      console.error('Failed to load product:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <p>Loading...</p>;
  if (!product) return <p>Product not found</p>;

  return (
    <div className="product-detail">
      <h1>{product.name}</h1>
      
      <ProductRating product={product} />
      
      <img src={`data:image/jpeg;base64,${product.imageBase64}`} alt={product.name} />
      
      <p>{product.description}</p>
      
      <h3>Rating Breakdown</h3>
      <RatingDistributionComponent productId={Number(productId)} />
      
      <h3>Customer Reviews</h3>
      <ReviewsList key={refreshReviews ? 'refresh' : 'no-refresh'} productId={Number(productId)} />
      
      <h3>Add Your Review</h3>
      <AddReviewForm productId={Number(productId)} onReviewAdded={() => setRefreshReviews(!refreshReviews)} />
    </div>
  );
};
```

---

## Ready to Code! 🎉

Copy these models, components, and services into your frontend project and start building the review UI!

**Happy coding!** 💻

