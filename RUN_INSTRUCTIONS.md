# Hướng dẫn chạy dự án Smart Clinic Management System

## Yêu cầu hệ thống
- Java 8 hoặc cao hơn
- Maven 3.6+
- MySQL 8.0+
- Git

## Bước 1: Cài đặt MySQL
1. Tải MySQL từ: https://dev.mysql.com/downloads/mysql/
2. Cài đặt MySQL Server với password mặc định (ví dụ: `password`)
3. Khởi động MySQL service

## Bước 2: Tạo database
```bash
# Kết nối MySQL
mysql -u root -p

# Tạo database và tables
source database_setup.sql
```

Hoặc chạy trực tiếp:
```bash
mysql -u root -p < database_setup.sql
```

## Bước 3: Cấu hình database connection
Chỉnh sửa file `app/src/main/resources/application.properties`:
```properties
# Thay đổi password MySQL nếu cần
spring.datasource.password=your_mysql_password
```

## Bước 4: Chạy ứng dụng
```bash
# Di chuyển vào thư mục app
cd app

# Chạy ứng dụng
mvn spring-boot:run
```

## Bước 5: Kiểm tra ứng dụng
- Ứng dụng sẽ chạy trên: http://localhost:8080
- API endpoints:
  - GET http://localhost:8080/api/doctors - Lấy danh sách bác sĩ
  - GET http://localhost:8080/api/patients - Lấy danh sách bệnh nhân
  - GET http://localhost:8080/api/appointments - Lấy danh sách cuộc hẹn

## Test API bằng curl

### Lấy tất cả bác sĩ:
```bash
curl -X GET "http://localhost:8080/api/doctors"
```

### Lấy bác sĩ theo chuyên khoa:
```bash
curl -X GET "http://localhost:8080/api/doctors/specialty/Cardiology"
```

### Tìm kiếm bác sĩ theo tên:
```bash
curl -X GET "http://localhost:8080/api/doctors/search?name=John"
```

### Lấy tất cả bệnh nhân:
```bash
curl -X GET "http://localhost:8080/api/patients"
```

## Test SQL Commands

### Kết nối MySQL:
```bash
mysql -u root -p smart_clinic_db
```

### Hiển thị tất cả tables:
```sql
SHOW TABLES;
```

### Lấy 5 bệnh nhân đầu tiên:
```sql
SELECT * FROM patients LIMIT 5;
```

### Chạy stored procedure:
```sql
CALL GetDailyAppointmentReportByDoctor(1, '2024-01-15');
CALL GetDoctorWithMostPatientsByMonth(1, 2024);
CALL GetDoctorWithMostPatientsByYear(2024);
```

## Troubleshooting

### Lỗi kết nối database:
- Kiểm tra MySQL đã chạy chưa
- Kiểm tra username/password trong application.properties
- Kiểm tra database đã được tạo chưa

### Lỗi Java version:
- Đảm bảo đang sử dụng Java 8
- Kiểm tra JAVA_HOME environment variable

### Lỗi Maven:
- Kiểm tra Maven đã cài đặt đúng chưa
- Chạy `mvn clean install` trước khi chạy ứng dụng

## Cấu trúc dự án
```
java-database-capstone/
├── app/
│   ├── src/main/java/com/project/back_end/
│   │   ├── models/          # Các entity classes
│   │   ├── controllers/     # REST controllers
│   │   ├── services/        # Business logic
│   │   ├── repo/           # Repository interfaces
│   │   └── BackEndApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── database_setup.sql       # Script tạo database
└── README.md
```
