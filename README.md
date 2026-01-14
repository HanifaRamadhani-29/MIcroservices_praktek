# UAS ARSITEKTUR BERBASIS LAYANAN

Implementasi Arsitektur Microservices pada Sistem Perpustakaan

Nama: Hanifa Ramadhani
NIM: 2311082020
Kelas: TRPL 3D

1. Pendahuluan

Proyek ini bertujuan untuk mengimplementasikan arsitektur microservices pada sistem Perpustakaan dengan menerapkan pendekatan Command Query Responsibility Segregation (CQRS) dan Event-Driven Architecture. Sistem dikembangkan menggunakan framework Spring Boot dan dideploy pada lingkungan Kubernetes. Untuk mendukung kebutuhan aplikasi modern, sistem ini dilengkapi dengan CI/CD Pipeline, mekanisme logging terpusat, serta fasilitas monitoring guna menjamin skalabilitas, keandalan, dan kemudahan dalam pemeliharaan sistem.

2. Teknologi Yang Digunakan
| Kategori           | Teknologi                 | Deskripsi                                                     |
|--------------------|---------------------------|----------------------------------------------------------------|
| Backend Framework  | Spring Boot 3.x           | Framework utama berbasis Java 17                               |
| Message Broker     | RabbitMQ                  | Media komunikasi asinkron dan sinkronisasi event               |
| Write Database     | MySQL 8.0                 | Menjamin konsistensi dan integritas data transaksi             |
| Read Database      | MongoDB                   | Optimasi query baca dengan struktur data yang fleksibel        |
| Service Discovery  | Netflix Eureka            | Registrasi dan penemuan layanan secara dinamis                 |
| API Gateway        | Spring Cloud Gateway      | Routing dan single entry point untuk seluruh API               |
| Orchestration      | Kubernetes                | Manajemen deployment dan alokasi resource container            |
| Logging            | ELK Stack                 | Logging terpusat lintas layanan                                |
| Monitoring         | Prometheus & Grafana      | Monitoring performa aplikasi dan penggunaan resource           |
| CI/CD              | Jenkins                   | Otomatisasi proses build, testing, dan deployment              |


3. 

