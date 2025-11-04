package com.hanifa.peminjaman_service.service;

import com.hanifa.peminjaman_service.config.RabbitMQConfig;
import com.hanifa.peminjaman_service.event.PeminjamanCreatedEvent;
import com.hanifa.peminjaman_service.event.PeminjamanDeletedEvent;
import com.hanifa.peminjaman_service.model.PeminjamanReadModel;
import com.hanifa.peminjaman_service.repository.PeminjamanReadModelRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service

public class PeminjamanEventConsumer {
    @Autowired
    private PeminjamanReadModelRepository peminjamanReadModelRepository;

    // Hapus ObjectMapper, kita tidak membutuhkannya lagi.

    // === LISTENER #1: KHUSUS UNTUK EVENT CREATED ===
    // Spring akan secara otomatis mengubah JSON menjadi objek PeminjamanCreatedEvent
    @RabbitListener(queues = RabbitMQConfig.EVENT_QUEUE_NAME)
    public void handlePeminjamanCreatedEvent(PeminjamanCreatedEvent event) {
        System.out.println("Processing PeminjamanCreatedEvent: " + event.getId());
        
        // Cek apakah data sudah ada untuk diupdate, atau buat baru jika belum ada.
        PeminjamanReadModel readModel = peminjamanReadModelRepository.findByPeminjamanIdSql(event.getId())
                .orElse(new PeminjamanReadModel());

        readModel.setPeminjamanIdSql(event.getId());
        readModel.setAnggotaId(event.getAnggotaId());
        readModel.setAnggotaNama(event.getAnggotaNama());
        readModel.setAnggotaEmail(event.getAnggotaEmail());
        readModel.setBukuId(event.getBukuId());
        readModel.setBukuJudul(event.getBukuJudul());
        readModel.setTanggalPeminjaman(event.getTanggalPeminjaman());
        readModel.setTanggalPengembalian(event.getTanggalPengembalian());

        peminjamanReadModelRepository.save(readModel);
        System.out.println("Saved/Updated PeminjamanReadModel for SQL ID: " + event.getId());
    }

    // === LISTENER #2: KHUSUS UNTUK EVENT DELETED (Jika Anda punya) ===
    // Anda bisa membuat listener terpisah untuk event lain
    // @RabbitListener(queues = RabbitMQConfig.EVENT_QUEUE_NAME)
    public void handlePeminjamanDeletedEvent(PeminjamanDeletedEvent event) {
        System.out.println("Processing PeminjamanDeletedEvent: " + event.getId());
        peminjamanReadModelRepository.findByPeminjamanIdSql(event.getId()).ifPresent(readModel -> {
            peminjamanReadModelRepository.delete(readModel);
            System.out.println("Deleted PeminjamanReadModel for SQL ID: " + event.getId());
        });
    }

    // Catatan: Dengan pendekatan di atas, jika ada event lain yang tidak
    // cocok dengan parameter method (misal PeminjamanDeletedEvent), Spring akan
    // mengabaikannya atau melempar error, bukan lagi mencetak "unknown event".
    // Untuk menangani berbagai tipe event di satu listener, diperlukan konfigurasi yang lebih advanced.
    // Memisahkan listener adalah cara paling bersih dan mudah.

}
