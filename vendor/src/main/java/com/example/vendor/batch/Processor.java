package com.example.vendor.batch;

import com.example.vendor.entity.Vendor;
import com.example.vendor.repository.VendorRepository;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<Vendor, Vendor> {
    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public Vendor process(Vendor vendor) throws Exception {
        return vendor;
    }
}
