# UAS ARSITEKTUR BERBASIS LAYANAN

Implementasi Arsitektur Microservices pada Sistem Perpustakaan

### Nama: Hanifa Ramadhani
### NIM: 2311082020
### Kelas: TRPL 3D

---

## 1. Pendahuluan

Proyek ini bertujuan untuk mengimplementasikan arsitektur microservices pada sistem Perpustakaan dengan menerapkan pendekatan Command Query Responsibility Segregation (CQRS) dan Event-Driven Architecture. Sistem dikembangkan menggunakan framework Spring Boot dan dideploy pada lingkungan Kubernetes. Untuk mendukung kebutuhan aplikasi modern, sistem ini dilengkapi dengan CI/CD Pipeline, mekanisme logging terpusat, serta fasilitas monitoring guna menjamin skalabilitas, keandalan, dan kemudahan dalam pemeliharaan sistem.

---

## 2. Teknologi yang Digunakan

| Kategori          | Teknologi            | Deskripsi                                                      |
|-------------------|----------------------|----------------------------------------------------------------|
| Backend Framework | Spring Boot 3.x      | Framework utama berbasis Java 17                               |
| Message Broker    | RabbitMQ             | Media komunikasi asinkron dan sinkronisasi event               |
| Write Database    | MySQL 8.0            | Menjamin konsistensi dan integritas data transaksi             |
| Read Database     | MongoDB              | Optimasi query baca dengan struktur data yang fleksibel        |
| Service Discovery | Netflix Eureka       | Registrasi dan penemuan layanan secara dinamis                 |
| API Gateway       | Spring Cloud Gateway | Routing dan single entry point untuk seluruh API               |
| Orchestration     | Kubernetes            | Manajemen deployment dan alokasi resource container            |
| Logging           | ELK Stack            | Logging terpusat lintas layanan                                |
| Monitoring        | Prometheus & Grafana | Monitoring performa aplikasi dan penggunaan resource           |
| CI/CD             | Jenkins               | Otomatisasi proses build, testing, dan deployment              |

---

## 3. Arsitektur Sistem dan Implementasi

### 3.1 Penerapan CQRS Hybrid

| Komponen        | Deskripsi                                                                 |
|-----------------|-----------------------------------------------------------------------------|
| Command Side    | Menangani operasi Create, Update, dan Delete melalui Command Handler dan menyimpan data ke MySQL sebagai write database |
| Event Publisher | Menerbitkan event ke RabbitMQ setelah proses persistensi data berhasil dilakukan |
| Format Event    | Event dikirim dalam format Map<String, Object> untuk menghindari masalah serialisasi Hibernate Proxy |
| Query Side      | Sync Consumer menerima event dari RabbitMQ dan memproyeksikan data ke MongoDB sebagai read model |
| Tujuan          | Memisahkan beban baca dan tulis agar performa sistem lebih optimal dan scalable |

---

### 3.2 Komunikasi Event-Driven Antar Layanan

Sistem menerapkan **Event-Driven Architecture (EDA)** untuk memastikan komunikasi antar layanan bersifat *loosely coupled* dan mudah dikembangkan.

| Proses          | Layanan Pengirim       | Event yang Dikirim        | Layanan Penerima   | Dampak |
|-----------------|------------------------|---------------------------|--------------------|--------|
| Peminjaman      | Service Peminjaman     | PeminjamanCreated         | Service Buku       | Status buku berubah menjadi **Dipinjam** |
| Pengembalian    | Service Pengembalian   | PengembalianProcessed     | Service Peminjaman | Status transaksi berubah menjadi **SELESAI** |
| Pengembalian    | Service Pengembalian   | PengembalianProcessed     | Service Buku       | Status buku berubah menjadi **Tersedia** |

## 4. Observability Sistem

### 4.1 Logging Terpusat (ELK Stack)

Sistem menerapkan logging terpusat menggunakan **ELK Stack** untuk memudahkan proses debugging dan analisis kesalahan.

| Komponen        | Deskripsi |
|-----------------|-----------|
| Log Collector   | Log dikirim ke Logstash melalui TCP port 5000 |
| Konfigurasi Log | Setiap layanan menggunakan file `logback-spring.xml` |
| Metadata Log    | Penambahan field `app_name` untuk mempermudah filtering di Kibana |
| Tujuan          | Memudahkan analisis log lintas layanan secara terpusat |

---

### 4.2 Monitoring Performa (Prometheus & Grafana)

Monitoring performa aplikasi dilakukan menggunakan **Prometheus** dan **Grafana**.

| Aspek           | Implementasi |
|-----------------|--------------|
| Metrics Export  | Micrometer Prometheus melalui endpoint `/actuator/prometheus` |
| Akses Metrics  | Kubernetes NodePort untuk scraping Prometheus eksternal |
| Visualisasi     | Grafana Dashboard (ID: 11378) |
| Metrik Dipantau | CPU usage, memory usage, dan latency HTTP request |

---

## 5. Deployment dan Optimasi Resource

### 5.1 Deployment di Kubernetes

Deployment aplikasi dilakukan menggunakan **Kubernetes manifest (YAML)**.

| Konfigurasi     | Fungsi |
|-----------------|--------|
| Namespace       | Isolasi resource antar lingkungan |
| Secrets         | Penyimpanan kredensial secara aman |
| Environment     | Konfigurasi aplikasi melalui `SPRING_APPLICATION_JSON` |

---

### 5.2 Strategi Optimasi RAM

Optimasi resource dilakukan untuk menyesuaikan dengan keterbatasan RAM.

| Strategi        | Implementasi |
|-----------------|--------------|
| JVM Heap        | `-Xms128M` dan `-Xmx256M` |
| Pod Memory Limit| Maksimal 384Mi |
| Infrastruktur  | Monitoring dan database dijalankan terpisah menggunakan Docker Compose |

---

## 6. CI/CD Pipeline Menggunakan Jenkins

Pipeline CI/CD diimplementasikan menggunakan **Jenkins Native**.

| Tahap           | Deskripsi |
|-----------------|-----------|
| Build           | Build aplikasi menggunakan Maven |
| Containerization| Build dan push Docker Image ke Docker Hub |
| Deployment      | Deployment otomatis ke Kubernetes dengan rolling update |
| Tujuan          | Menjamin konsistensi deployment dan meminimalkan downtime |

---

## 7. Akses Dashboard Sistem

| Komponen        | URL |
|-----------------|-----|
| API Gateway     | http://localhost:30000 |
| Eureka Server   | http://localhost:8761 |
| Kibana          | http://localhost:5601 |
| Grafana         | http://localhost:3000 |
| RabbitMQ        | http://localhost:15672 |
| phpMyAdmin      | http://localhost:32000 |
| Mongo Express   | http://localhost:8085 |

---

## 8. Kesimpulan

Sistem Manajemen Perpustakaan ini berhasil mengimplementasikan arsitektur **Microservices modern** dengan pendekatan **CQRS Hybrid** dan **Event-Driven Architecture**. Pemisahan database antara proses tulis dan baca meningkatkan performa sistem tanpa mengorbankan integritas data transaksi.

Dukungan **Kubernetes**, **CI/CD Jenkins**, serta **Logging dan Monitoring terpusat** menjadikan sistem siap untuk dikembangkan lebih lanjut dan dioperasikan pada lingkungan produksi terdistribusi.


