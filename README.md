**TRƯỜNG ĐẠI HỌC SƯ PHẠM KỸ THUẬT**

**KHOA CÔNG NGHỆ SỐ**

<img width="78" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/a8f87fc5-7e21-4b55-8321-03cf1ffde0f2">


# **BÁO CÁO**

# **HỌC PHẦN LẬP TRÌNH TRÊN ĐIỆN THOẠI DI ĐỘNG**

**ĐỀ TÀI : XÂY DỰNG ỨNG DỤNG QUẢN LÝ VÀ ĐẶT MÓN NHÀ HÀNG - FOOD ORDERING SPOON**

**Nhóm SVTH : Lê Hà Bình – 21115053120105**

**Lê Kim Nam – 21115053120128**

**Lê Ngọc Hào – 21115053120116**

**Diệp Văn Tý – 21115053120161**

**Trương Minh Mẫn - 2111514110113**

**LỚP : 123LTTD02**

**CBHD : ThS. Đỗ Phú Huy**

**ĐÀ NẴNG, tháng 12 năm 2023.**

## **MỤC LỤC**

**[CHƯƠNG 1: TỔNG QUAN ĐỀ TÀI 2](#_Toc153551224)**

[1.1.Lý do chọn đề tài 2](#_Toc153551225)

[1.2.Mục tiêu đề tài 2](#_Toc153551226)

[1.3.Yêu cầu đề tài 2](#_Toc153551227)

[1.4.Phạm vi đề tài 3](#_Toc153551228)

**[CHƯƠNG 2: GIAO DIỆN VÀ CHỨC NĂNG 4](#_Toc153551229)**

[2.1.Trang Giới thiệu 4](#_Toc153551230)

[2.2.Trang Đăng ký, Đăng nhập 5](#_Toc153551231)

[2.3.Trang chủ 6](#_Toc153551232)

[2.4.Trang Chi tiết món ăn 7](#_Toc153551233)

[2.5.Trang Giỏ hàng 8](#_Toc153551234)

[2.6.Trang Xác nhận thông tin 9](#_Toc153551235)

[2.7.Trang Lịch sử đặt món 10](#_Toc153551236)

[2.8.Trang Cá nhân 11](#_Toc153551237)

[2.9.Trang Tổng quan (admin) 12](#_Toc153551238)

[2.10.Trang Danh sách món (admin) 13](#_Toc153551239)

[2.11.Trang Thêm món (admin) 14](#_Toc153551240)

[2.12.Trang Cập nhật món (admin) 15](#_Toc153551241)

[2.13.Trang Thống kê (admin) 16](#_Toc153551242)

[2.14.Trang Chi tiết đơn hàng (admin) 17](#_Toc153551243)

**[CHƯƠNG 3: CƠ SỞ DỮ LIỆU VÀ SERVICE 18](#_Toc153551244)**

[3.1.Hệ quản trị MongoDB 18](#_Toc153551245)

[3.2.Dữ liệu dạng JSON 18](#_Toc153551246)

[3.3.Các Service được sử dụng 20](#_Toc153551247)

## **CHƯƠNG 1: TỔNG QUAN ĐỀ TÀI**

  1. **Lý do chọn đề tài**

- Trên thị trường thực phẩm ngày nay, việc có thể tìm và lựa chọn cho bản thân một nhà hàng kinh doanh thực phẩm uy tín và chất lượng giữa vô vàn các nhà hàng trên thị trường là việc vô cũng nan giải. Nhưng kể cả nếu đã có cho mình một nơi như vậy nhưng việc phải luôn phải chờ đợi hàng giờ đồng hồ di chuyển đến nhà hàng và đợi được phục vụ đôi khi lại mang lại cảm giác không thoải mái cho khách hàng. Vậy nên, dựa trên cơ sở đó, nhóm chúng em đã quyết định phát triển một ứng dụng giúp đặt món ăn dành cho khách hàng.

  1. **Mục tiêu đề tài**

- Xây dựng một ứng dụng với giao diện thân thiện, bắt mắt, dễ sử dụng với khách hàng.
- Giúp nhà hàng kiểm soát được tình trạng quá tải khách hàng khi giảm tải việc đó qua việc khách có thể gọi món từ xa và được giao hàng qua đội ngũ giao hàng của nhà hàng.
- Giúp khách hàng có thể dễ dàng lựa chọn và thưởng thức các món ăn của nhà hàng mà không cần nhất thiết phải đến nhà hàng như trước.

  1. **Yêu cầu đề tài**

- **Yêu cầu về nền tảng:**

+ Ứng dụng được phát triển trên hệ điều hành phổ biến trên điện thoại là Android.

- **Yêu cầu về dữ liệu:**

+ Hệ quản trị cơ sở dữ liệu : SQLite

+ Quản lý dữ liệu người dùng: thông tin cá nhân khách hàng, thông tin lịch sự mua hàng của khách hàng, thông tin món ăn tại quán,…

- **Yêu cầu về hiệu năng:**

+ Tốc độ truyền tải: nhanh chóng, thuận tiện, đặc biệt không bị gián đoạn khi khách hàng đang lựa chọn và đặt hàng món ăn.

+ Thời gian tải dữ liệu: Quá trình tải lên giao diện của nhà hàng không được quá chậm, đặc biệt trong việc xem chi tiết về các món ăn.

+ Khả năng kiểm soát truy cập: Hệ thống có khả năng duy trì ổn định trước trường hợp có sự truy cập đồng thời từ rất nhiều khách hàng cùng một thời điểm mà không bị giảm đi hiệu suất của ứng dụng.

- **Yêu cầu về bảo mật:**

+ Xác thực thông tin người dùng: Cung cấp các phương thức xác thực an toàn thông tin cho người dùng như tài khoản, mật khẩu, email,..

+ Quản lý quyền truy cập: Xác định và quản lý quyền hạn truy cập cụ thể của khách hàng đối với các tính năng và dữ liệu cụ thể.

- **Yêu cầu phi chức năng:**

+ Yêu cầu về giao diện người dùng (UI/UX): Thiết kế đơn giản, bắt mắt, thân thiện và dễ sử dụng đối với khách hàng, tốc độ xử lý nhanh chóng và mượt mà khi tương tác,..

  1. **Phạm vi đề tài**

- Áp dụng cho khách hàng có nhu cầu đặt thức ăn từ nhà hàng trên nội địa tỉnh thành phố nhất định.

## **CHƯƠNG 2: GIAO DIỆN VÀ CHỨC NĂNG**

  1. **Trang Giới thiệu**

- Khi người dùng mở app, sẽ xuất hiện Trang Giới thiệu.

<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/c249178b-e2f3-4b4d-91d2-142cf7ec96b7">
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/4f847a22-09ae-4aa5-956c-da452f092e93">

  2. **Trang Đăng ký, Đăng nhập**

- Khi người dùng nhấn nút "Create an account" ở trang Giới thiệu, hoặc nhấn "Click here" ở trang Đăng nhập, sẽ xuất hiện trang Đăng ký.

<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/67aae7b7-4c66-48e2-9e64-fb3f2212e9f1">


- Khi người dùng nhấn "Already have account" ở trang Giới thiệu, hoặc nhấn "Click here" ở trang Đăng ký, sẽ xuất hiện trang Đăng nhập.

<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/2c5a8ace-7772-4ecd-b20f-c767f5d00182">

  3. **Trang chủ**

- Tài khoản: 0123456782, mật khẩu: 12345
- Khi người dùng đăng nhập thành công, sẽ xuất hiện Trang chủ.
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/8d548974-8a02-42de-95bd-13f5e32d160e">


######


  4. **Trang Chi tiết món ăn**

- Khi người dùng chọn một món ở Trang chủ, sẽ xuất hiện trang Chi tiết.
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/ddf38eeb-f314-49c4-b8c9-1a7cca0cb4ea">


- Khi người dùng nhấn nút "Add to cart", sẽ xuất hiện thông báo "Add to cart successfully".
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/24db0d78-b889-4cfd-9aac-5cbbc5b67195">


  5. **Trang Giỏ hàng**

- Khi người dùng nhấn biểu tượng "Giỏ hàng" trên thanh menu, sẽ xuất hiện trang Giỏ hàng.
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/5ac9ec9c-dcd5-44c7-87c5-f6c7c30d623f">


- Khi người dùng nhấn biểu tượng "Xoá", sẽ xuất hiện hộp thoại.
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/8cab254f-e10f-4e1f-a8e6-74e2647ebb63">


  6. **Trang Xác nhận thông tin**

- Khi người dùng nhấn nút "Checkout" trên trang Giỏ hàng, sẽ xuất hiện trang Xác nhận.
- Khi người dùng nhấn nút "Buy now" trên trang Xác nhận, sẽ xuất hiện hộp thoại.
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/1c9b094c-0dd1-4810-86b3-74fdb6036730">
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/5fafbcb6-32e4-476d-ae3f-9a91708f318d">


  7. **Trang Lịch sử đặt món**

- Khi người dùng nhấn biểu tượng "Lịch sử" ở góc phải trên cùng trang Giỏ hàng, sẽ xuất hiện trang Lịch sử.
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/51ddf510-f704-427a-aa42-1cca216d885f">


  8. **Trang Cá nhân**

- Khi người dùng nhấn biểu tượng "Cá nhân" trên thanh menu, sẽ xuất hiện trang Cá nhân.
- Khi người dùng nhấn nút "Edit profile" trên trang Cá nhân, sẽ xuất hiện trang chỉnh sửa thông tin cá nhân.
- Khi người dùng nhấn biểu tượng "Log out" ở góc phải trên cùng trang Cá nhân, sẽ xuất hiện trang Đăng nhập.
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/89ebf39a-b9ca-4382-aa55-254b72f01747">
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/e6d3b61e-f726-484f-8365-3ba3cc8828cc">


  9. **Trang Tổng quan (admin)**

- Tài khoản admin: 0123456789, mật khẩu: 12345
- Khi đăng nhập vào tài khoản admin, sẽ xuất hiện trang Tổng quan để admin chọn chức năng.
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/c4f24fd5-db32-46ae-8916-b5851e738b02">


  10. **Trang Danh sách món (admin)**

- Khi admin chọn "Foods" ở trang Tổng quan, sẽ xuất hiện trang Danh sách món.
- Khi admin tick chọn món ăn, và nhấn nút "Remove", sẽ xuất hiện hộp thoại.
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/df7aee5f-5f26-4ff9-9398-bd986bcb7ad1">
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/0cf5036e-7a68-4c84-b7a4-7894a7b32e80">


  11. **Trang Thêm món (admin)**

- Khi admin nhấn nút " + Add a new dish" ở trang Tổng quan, hoặc nhấn nút " + Add" ở trang Danh sách món, sẽ xuất hiện trang Thêm món.
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/afd915f0-b8e4-45e5-8bf2-8d943f34bc7d">


  12. **Trang Cập nhật món (admin)**

- Khi admin nhấn nút "Edit" ở trang Danh sách món, sẽ xuất hiện trang Cập nhật món.
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/34594852-f837-4cbe-833a-b000ec63f141">


  13. **Trang Thống kê (admin)**

- Khi admin chọn "Statistics" ở trang Tổng quan, sẽ xuất hiện trang Thống kê đơn đặt hàng.
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/da7a171b-8d11-4ab4-a407-d55e82c2dc12">


  14. **Trang Chi tiết đơn hàng (admin)**

- Khi admin chọn một đơn hàng ở trang Thống kê, sẽ xuất hiện trang Chi tiết.
<img width="129" alt="image" src="https://github.com/minhman293/demo_android_team/assets/69661294/f1eb011a-58a9-4a83-a61a-eee62da87e9a">


## **CHƯƠNG 3: CƠ SỞ DỮ LIỆU VÀ SERVICE**

  1. **Hệ quản trị MongoDB**

  2. **Dữ liệu dạng JSON**
- Collection adminfirebasetokens
- Collection categories
- Collection products
- Collection users

3. **Các Service được sử dụng**

- Web Service được xây dựng trên môi trường Node.js và được kết nối cơ sở dữ liệu mongoDB.
- Sử dụng Firebase Cloud Messaging để tạo và nhận thông báo.
- Sử dụng Google Authentication để đăng nhập bằng Gmail.

