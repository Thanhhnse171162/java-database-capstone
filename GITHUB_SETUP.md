# Hướng dẫn Push Code lên GitHub

## Bước 1: Khởi tạo Git repository
```bash
# Trong thư mục java-database-capstone
git init
git add .
git commit -m "Initial commit: Smart Clinic Management System"
```

## Bước 2: Kết nối với GitHub repository
```bash
# Thay thế username bằng tên GitHub của bạn
git remote add origin https://github.com/Thanhhnse171162/java-database-capstone.git
git branch -M main
git push -u origin main
```

## Bước 3: Kiểm tra repository
Sau khi push thành công, kiểm tra các URL:
- https://github.com/Thanhhnse171162/java-database-capstone/blob/main/user_stories.md
- https://github.com/Thanhhnse171162/java-database-capstone/blob/main/schema-design.md
- https://github.com/Thanhhnse171162/java-database-capstone/blob/main/app/src/main/java/com/project/back_end/models/Doctor.java

## Bước 4: Cập nhật code (nếu cần)
```bash
# Sau khi chỉnh sửa code
git add .
git commit -m "Update: Improved schema design with data types and foreign keys"
git push origin main
```

## Lưu ý quan trọng:
1. **Đảm bảo tất cả file đã được tạo** trước khi push
2. **Kiểm tra repository có public** để các URL hoạt động
3. **Test các URL** sau khi push để đảm bảo chúng hoạt động
