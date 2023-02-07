package com.example.vendor.batch;

import com.example.vendor.entity.Vendor;
import com.example.vendor.repository.VendorRepository;
import org.springframework.stereotype.Component;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class Writer implements ItemWriter<Vendor> {

    @Autowired
    private VendorRepository repo;

    @Override
    @Transactional
    public void write(List<? extends Vendor> vendor) throws Exception {
        repo.saveAll(vendor);
    }
}
