package com.siklus.application.service.impl;

import com.siklus.application.SampahReq;
import com.siklus.application.model.Sampah;
import com.siklus.application.model.User;
import com.siklus.application.repository.SampahRepository;
import com.siklus.application.repository.UserRepository;
import com.siklus.application.service.SampahService;
import org.springframework.stereotype.Service;

@Service
public class SampahServiceImpl implements SampahService {

    private final SampahRepository sampahRepository;
    private final UserRepository userRepository;

    public SampahServiceImpl(
            SampahRepository sampahRepository,
            UserRepository userRepository
    ) {
        this.sampahRepository = sampahRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Sampah saveSampah(SampahReq request) {

        User user = userRepository.findById(request.getIdUser())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        Sampah sampah = new Sampah();
        sampah.setDateSampah(request.getDateSampah());
        sampah.setJnsSampah(request.getJnsSampah());
        sampah.setBrtSampah(request.getBrtSampah());
        sampah.setRwSampah(user.getRwUser());

        return sampahRepository.save(sampah);
    }
}
