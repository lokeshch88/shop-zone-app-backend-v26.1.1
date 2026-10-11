# ⚡ QUICK START - Review System

## 🚀 Get Started in 5 Minutes

### Step 1: Database Setup (1 min)
```bash
# Option A: Automatic (Easiest)
# Edit application.properties:
spring.jpa.hibernate.ddl-auto=update

# Option B: Manual SQL
# Copy SQL from DATABASE_SCHEMA.md
# Execute in MySQL
```

### Step 2: Compile (1 min)
```bash
mvn clean compile
```

### Step 3: Run Application (1 min)
```bash
mvn spring-boot:run
```

### Step 4: Test with curl (1 min)
```bash
# Create a review
curl -X POST "http://localhost:8080/api/reviews/products/1" \
  -H "Authorization: Bearer {token}" \
  -d "rating=5&title=Great!&content=Amazing product"

# Get product reviews
curl "http://localhost:8080/api/reviews/products/1?page=0&size=20"
```

### Step 5: Integrate (1 min)
Look at `REVIEW_API_DOCUMENTATION.md` for all endpoints

---

## 📚 Documentation Map

| Document | What It Is | Read Time |
|----------|-----------|-----------|
| **README_DOCUMENTATION.md** | Index of all docs | 5 min |
| **SESSION_SUMMARY.md** | What was built | 10 min |
| **IMPLEMENTATION_COMPLETE.md** | Overview | 15 min |
| **REVIEW_API_DOCUMENTATION.md** | API reference | 20 min |
| **INTEGRATION_GUIDE.md** | Setup guide | 15 min |
| **DATABASE_SCHEMA.md** | SQL details | 10 min |

**Start with:** README_DOCUMENTATION.md ⭐

---

## 🎯 Most Used Commands

### Create Review
```bash
curl -X POST "http://localhost:8080/api/reviews/products/1" \
  -H "Authorization: Bearer {token}" \
  -d "rating=5&title=Great!&content=Love it"
```

### Get Product Reviews
```bash
curl "http://localhost:8080/api/reviews/products/1?page=0&size=20"
```

### Add Photo
```bash
curl -X POST "http://localhost:8080/api/reviews/1/photos" \
  -H "Authorization: Bearer {token}" \
  -F "photo=@image.jpg"
```

### Merchant Reply
```bash
curl -X POST "http://localhost:8080/api/reviews/1/replies" \
  -H "Authorization: Bearer {merchant_token}" \
  -d "replyText=Thank you!&replyType=THANK_YOU"
```

### Admin Approve
```bash
curl -X POST "http://localhost:8080/api/reviews/admin/1/approve" \
  -H "Authorization: Bearer {admin_token}"
```

---

## 🔑 Key Endpoints

```
POST   /api/reviews/products/{id}           Create review
GET    /api/reviews/products/{id}           Get reviews
PUT    /api/reviews/{id}                    Edit review
DELETE /api/reviews/{id}                    Delete review
POST   /api/reviews/{id}/photos             Add photo
POST   /api/reviews/{id}/replies            Merchant reply
GET    /api/reviews/products/{id}/average-rating   Get rating
GET    /api/reviews/products/{id}/rating-distribution   Get breakdown
```

---

## 📊 Database Tables

### product_reviews
- review_id (PK)
- product_id (FK)
- user_id (FK)
- rating (1-5) ⭐ REQUIRED
- title (optional)
- content (optional)
- is_verified_purchase
- is_approved
- UNIQUE (product_id, user_id)

### review_photos
- photo_id (PK)
- review_id (FK)
- photo_data (BLOB)
- display_order (1-5)

### merchant_replies
- reply_id (PK)
- review_id (FK)
- merchant_id (FK)
- reply_text
- reply_type

### products (MODIFIED)
- + average_rating
- + total_reviews

---

## ✅ Feature List

- ⭐ 1-5 star rating (mandatory)
- 📝 Optional review text
- 📸 Max 5 photos per review
- 💬 Merchant can reply
- ✅ Admin approval workflow
- 🏷️ Verified purchase badge
- 📊 Rating calculation
- 🔍 Filter by rating
- 📈 Rating distribution
- 🎯 One review per user per product

---

## 🛡️ Security

- ✅ Only edit own reviews
- ✅ Only delete own reviews
- ✅ Only add photos to own reviews
- ✅ Merchant reply protected
- ✅ Admin actions protected
- ✅ Input validation
- ✅ UNIQUE constraint in DB

---

## 🧪 Quick Tests

```bash
# Test 1: Create review
GET ProductId 1
POST /api/reviews/products/1 rating=5

# Test 2: Get reviews
GET /api/reviews/products/1?page=0&size=20

# Test 3: Add photo
POST /api/reviews/1/photos photo=@test.jpg

# Test 4: Merchant reply
POST /api/reviews/1/replies replyText=Thank

# Test 5: Admin approve
POST /api/reviews/admin/1/approve

# Expected: All return 200/201
```

---

## 💻 Code Files

**Service:** `ReviewService.java` (350 lines)  
**Controller:** `ReviewController.java` (400 lines)  
**DTOs:** `ProductReviewDTO.java`, `ReviewPhotoDTO.java`, `MerchantReplyDTO.java`  
**Updated:** `ProductService.java`, `ProductController.java`  

---

## 📋 Checklist

- [ ] Read README_DOCUMENTATION.md
- [ ] Setup database
- [ ] mvn clean compile
- [ ] mvn spring-boot:run
- [ ] Test one curl command
- [ ] Read REVIEW_API_DOCUMENTATION.md
- [ ] Test all endpoints
- [ ] Integrate with frontend
- [ ] Deploy

---

## 🆘 Troubleshooting

**Q: Compilation error?**
A: Check Java version (11+), Maven version (3.6+)

**Q: Database tables not created?**
A: Check spring.jpa.hibernate.ddl-auto=update in application.properties

**Q: 403 error on endpoints?**
A: Add Authorization header with JWT token

**Q: 400 rating value error?**
A: Ensure rating is 1-5 integer

**Q: Column not found?**
A: Run database migration (copy SQL from DATABASE_SCHEMA.md)

---

## 📞 Most Important Files

1. **REVIEW_API_DOCUMENTATION.md** - Use this for API reference
2. **ReviewService.java** - Read this to understand logic
3. **ReviewController.java** - Read this for endpoints
4. **SESSION_SUMMARY.md** - Read this for overview

---

## 🎉 You're Ready!

Your review system is ready to use. Pick one:

**Option 1: Quick Integration**
→ Copy-paste curl commands from REVIEW_API_DOCUMENTATION.md

**Option 2: Full Understanding**
→ Read all docs in README_DOCUMENTATION.md order

**Option 3: Just Coding**
→ Open ReviewService.java and ReviewController.java

---

## 🚀 Next: Frontend Integration

Your API is ready. Now build your frontend:

```javascript
// Example: Create review
fetch('/api/reviews/products/1', {
  method: 'POST',
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/x-www-form-urlencoded'
  },
  body: 'rating=5&title=Great!&content=...'
})

// Example: Get reviews
fetch('/api/reviews/products/1?page=0&size=20')
  .then(r => r.json())
  .then(data => console.log(data.reviews))
```

---

## ✨ Ready to Deploy!

Your ShopZone review system is **production-ready**. 

**Start:** README_DOCUMENTATION.md  
**Reference:** REVIEW_API_DOCUMENTATION.md  
**Deploy:** Follow INTEGRATION_GUIDE.md  

**Happy coding!** 🎊

