# 📚 Review System - Complete API Documentation

## 🎯 Base URL
```
http://localhost:8080/api/reviews
```

## ✨ Overview

This API provides complete functionality for product reviews, ratings, photos, and merchant replies in ShopZone ecommerce platform.

---

## 🔑 Authentication
All endpoints (except public GET) require:
```
Authorization: Bearer {JWT_TOKEN}
```

---

## 📋 API Endpoints

### 1️⃣ CREATE REVIEW
**Endpoint:** `POST /api/reviews/products/{productId}`

**Description:** Create a new review for a product (max 1 per user per product)

**Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| productId | Long | ✅ | Product ID to review |
| rating | Integer | ✅ | 1-5 stars |
| title | String | ❌ | Review title (5-500 chars) |
| content | String | ❌ | Review text (10-3000 chars) |

**Request:**
```bash
curl -X POST "http://localhost:8080/api/reviews/products/1" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "rating=5&title=Great Product&content=This is an amazing product..."
```

**Response (201 Created):**
```json
{
  "id": 1,
  "productId": 1,
  "userId": 10,
  "username": "john_doe",
  "rating": 5,
  "title": "Great Product",
  "content": "This is an amazing product...",
  "isVerifiedPurchase": true,
  "isApproved": false,
  "createdAt": "2024-08-31T10:30:00",
  "updatedAt": "2024-08-31T10:30:00"
}
```

**Error (400):**
```json
{
  "error": "Rating must be between 1 and 5"
}
```

---

### 2️⃣ UPDATE REVIEW (Own reviews only)
**Endpoint:** `PUT /api/reviews/{reviewId}`

**Description:** Update an existing review

**Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| reviewId | Long | ✅ | Review ID |
| rating | Integer | ❌ | 1-5 stars |
| title | String | ❌ | Review title |
| content | String | ❌ | Review text |

**Request:**
```bash
curl -X PUT "http://localhost:8080/api/reviews/1" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "rating=4&title=Good Product"
```

**Response (200 OK):**
```json
{
  "id": 1,
  "rating": 4,
  "title": "Good Product",
  "updatedAt": "2024-08-31T11:00:00"
}
```

---

### 3️⃣ DELETE REVIEW (Own reviews only)
**Endpoint:** `DELETE /api/reviews/{reviewId}`

**Description:** Delete a review

**Request:**
```bash
curl -X DELETE "http://localhost:8080/api/reviews/1" \
  -H "Authorization: Bearer {token}"
```

**Response (200 OK):**
```json
{
  "message": "Review deleted successfully"
}
```

---

### 4️⃣ GET PRODUCT REVIEWS (Public)
**Endpoint:** `GET /api/reviews/products/{productId}?page=0&size=20`

**Description:** Get all approved reviews for a product (paginated)

**Parameters:**
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| productId | Long | ✅ | Product ID |
| page | Integer | 0 | Page number (0-indexed) |
| size | Integer | 20 | Reviews per page |

**Request:**
```bash
curl -X GET "http://localhost:8080/api/reviews/products/1?page=0&size=20"
```

**Response (200 OK):**
```json
{
  "totalElements": 45,
  "totalPages": 3,
  "currentPage": 0,
  "reviews": [
    {
      "id": 1,
      "rating": 5,
      "title": "Excellent!",
      "username": "john_doe",
      "isVerifiedPurchase": true,
      "createdAt": "2024-08-31T10:30:00",
      "photos": [
        {
          "id": 1,
          "photoBase64": "iVBORw0KGgo...",
          "displayOrder": 1
        }
      ],
      "replies": [
        {
          "id": 1,
          "merchantName": "ShopZone",
          "replyText": "Thank you for your feedback!",
          "replyType": "THANK_YOU"
        }
      ]
    }
  ]
}
```

---

### 5️⃣ GET REVIEWS BY RATING (Public)
**Endpoint:** `GET /api/reviews/products/{productId}/rating/{rating}?page=0&size=20`

**Description:** Get reviews filtered by star rating

**Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| productId | Long | Product ID |
| rating | Integer | 1-5 stars |

**Request:**
```bash
curl "http://localhost:8080/api/reviews/products/1/rating/5?page=0&size=20"
```

**Response:**
```json
{
  "rating": 5,
  "totalElements": 12,
  "reviews": [...]
}
```

---

### 6️⃣ GET USER REVIEWS (Public)
**Endpoint:** `GET /api/reviews/users/{userId}?page=0&size=20`

**Description:** Get all reviews by a specific user

**Request:**
```bash
curl "http://localhost:8080/api/reviews/users/10?page=0&size=20"
```

---

### 7️⃣ GET RATING DISTRIBUTION (Public)
**Endpoint:** `GET /api/reviews/products/{productId}/rating-distribution`

**Description:** Get breakdown of reviews by star rating

**Request:**
```bash
curl "http://localhost:8080/api/reviews/products/1/rating-distribution"
```

**Response:**
```json
{
  "1 Star": 2,
  "2 Star": 1,
  "3 Star": 5,
  "4 Star": 10,
  "5 Star": 27
}
```

---

### 8️⃣ GET AVERAGE RATING (Public)
**Endpoint:** `GET /api/reviews/products/{productId}/average-rating`

**Description:** Get average rating for a product

**Request:**
```bash
curl "http://localhost:8080/api/reviews/products/1/average-rating"
```

**Response:**
```json
{
  "averageRating": 4.67
}
```

---

### 9️⃣ ADD PHOTO TO REVIEW
**Endpoint:** `POST /api/reviews/{reviewId}/photos`

**Description:** Add a photo to a review (max 5 photos per review)

**Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| reviewId | Long | ✅ | Review ID |
| photo | File | ✅ | Image file (JPEG, PNG) |
| displayOrder | Integer | ❌ | 1-5 (default: 1) |

**Request:**
```bash
curl -X POST "http://localhost:8080/api/reviews/1/photos" \
  -H "Authorization: Bearer {token}" \
  -F "photo=@/path/to/image.jpg" \
  -F "displayOrder=1"
```

**Response (201 Created):**
```json
{
  "id": 1,
  "reviewId": 1,
  "photoBase64": "iVBORw0KGgo...",
  "displayOrder": 1,
  "uploadedAt": "2024-08-31T10:35:00"
}
```

**Error (400):**
```json
{
  "error": "Maximum 5 photos allowed per review"
}
```

---

### 🔟 DELETE PHOTO
**Endpoint:** `DELETE /api/reviews/photos/{photoId}`

**Request:**
```bash
curl -X DELETE "http://localhost:8080/api/reviews/photos/1" \
  -H "Authorization: Bearer {token}"
```

---

### 1️⃣1️⃣ MERCHANT REPLY TO REVIEW
**Endpoint:** `POST /api/reviews/{reviewId}/replies`

**Description:** Merchant/Admin replies to a review

**Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| reviewId | Long | ✅ | Review ID |
| replyText | String | ✅ | Reply (5-2000 chars) |
| replyType | String | ❌ | RESOLUTION, THANK_YOU, APOLOGY |

**Request:**
```bash
curl -X POST "http://localhost:8080/api/reviews/1/replies" \
  -H "Authorization: Bearer {merchant_token}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "replyText=Thank you for your feedback! We appreciate it.&replyType=THANK_YOU"
```

**Response (201 Created):**
```json
{
  "id": 1,
  "reviewId": 1,
  "merchantId": 5,
  "merchantName": "ShopZone Store",
  "replyText": "Thank you for your feedback!",
  "replyType": "THANK_YOU",
  "createdAt": "2024-08-31T10:40:00"
}
```

---

### 1️⃣2️⃣ GET REPLIES FOR REVIEW
**Endpoint:** `GET /api/reviews/{reviewId}/replies`

**Description:** Get all replies to a review

**Request:**
```bash
curl "http://localhost:8080/api/reviews/1/replies"
```

**Response:**
```json
{
  "replies": [
    {
      "id": 1,
      "merchantName": "ShopZone",
      "replyText": "Thank you for your feedback",
      "replyType": "THANK_YOU",
      "createdAt": "2024-08-31T10:40:00"
    }
  ]
}
```

---

## 🛡️ ADMIN ENDPOINTS

### APPROVE REVIEW
**Endpoint:** `POST /api/reviews/admin/{reviewId}/approve`

**Request:**
```bash
curl -X POST "http://localhost:8080/api/reviews/admin/1/approve" \
  -H "Authorization: Bearer {admin_token}"
```

**Response:**
```json
{
  "message": "Review approved successfully"
}
```

---

### REJECT REVIEW
**Endpoint:** `POST /api/reviews/admin/{reviewId}/reject`

**Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| reviewId | Long | ✅ | Review ID |
| reason | String | ✅ | Rejection reason |

**Request:**
```bash
curl -X POST "http://localhost:8080/api/reviews/admin/1/reject" \
  -H "Authorization: Bearer {admin_token}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "reason=Contains inappropriate language"
```

---

### GET UNAPPROVED REVIEWS
**Endpoint:** `GET /api/reviews/admin/unapproved?page=0&size=20`

**Description:** Get pending reviews for moderation

**Request:**
```bash
curl "http://localhost:8080/api/reviews/admin/unapproved?page=0" \
  -H "Authorization: Bearer {admin_token}"
```

---

## 🛍️ PRODUCT ENDPOINTS WITH REVIEWS

### GET PRODUCT WITH REVIEWS
**Endpoint:** `GET /products/{productId}/with-reviews`

**Description:** Get product details including all reviews

**Request:**
```bash
curl "http://localhost:8080/products/1/with-reviews"
```

**Response:**
```json
{
  "id": 1,
  "name": "iPhone 15",
  "averageRating": 4.67,
  "totalReviews": 45,
  "reviews": [
    {
      "id": 1,
      "rating": 5,
      "title": "Excellent!",
      "username": "john_doe",
      "photos": [...],
      "replies": [...]
    }
  ]
}
```

---

### GET PRODUCT WITH RATING
**Endpoint:** `GET /products/{productId}/with-rating`

**Description:** Get product with rating summary

**Request:**
```bash
curl "http://localhost:8080/products/1/with-rating"
```

---

## 🧪 Example Workflows

### Complete Review Submission Flow

1. **Create Review**
```bash
curl -X POST "http://localhost:8080/api/reviews/products/1" \
  -H "Authorization: Bearer {token}" \
  -d "rating=5&title=Great!&content=Amazing product"
# Returns: reviewId = 1
```

2. **Add Photo**
```bash
curl -X POST "http://localhost:8080/api/reviews/1/photos" \
  -H "Authorization: Bearer {token}" \
  -F "photo=@photo.jpg" \
  -F "displayOrder=1"
```

3. **Merchant Replies** (after admin approval)
```bash
curl -X POST "http://localhost:8080/api/reviews/1/replies" \
  -H "Authorization: Bearer {merchant_token}" \
  -d "replyText=Thank you!&replyType=THANK_YOU"
```

---

## ⚠️ Error Responses

### 400 Bad Request
```json
{
  "error": "Rating must be between 1 and 5"
}
```

### 403 Forbidden
```json
{
  "error": "You can only edit your own review"
}
```

### 404 Not Found
```json
{
  "error": "Review not found"
}
```

### 500 Internal Server Error
```json
{
  "error": "Failed to create review: Database connection error"
}
```

---

## 📊 HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200 | OK - Request succeeded |
| 201 | Created - Resource created |
| 400 | Bad Request - Invalid data |
| 403 | Forbidden - No permission |
| 404 | Not Found - Resource doesn't exist |
| 500 | Internal Server Error |

---

## 🔒 Security Rules

1. **User can only:**
   - Create 1 review per product
   - Edit/delete own reviews
   - Add photos to own reviews

2. **Merchant can:**
   - Reply to reviews of their products only
   - Cannot reply to other merchant's products

3. **Admin can:**
   - Approve/reject all reviews
   - See unapproved reviews queue

4. **All users can:**
   - View approved reviews
   - View merchant replies
   - Get rating distributions

---

## 📞 Support
For issues or questions, contact: support@shopzone.com

