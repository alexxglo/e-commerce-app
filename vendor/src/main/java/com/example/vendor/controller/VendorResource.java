package com.example.vendor.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.example.vendor.entity.Vendor;
import com.example.vendor.exceptions.VendorNotFoundException;
import com.example.vendor.repository.VendorRepository;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class VendorResource {

    @Autowired
    private VendorRepository vendorRepository;

    @GetMapping("/vendors")
    public List<Vendor> retrieveAllVendors() {
        return vendorRepository.findAll();
    }

    @Retry(name = "vendorSearch")
    @GetMapping("/vendors/{id}")
    public EntityModel<Vendor> retrieveVendor(@PathVariable long id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);

        if (!vendor.isPresent())
            throw new VendorNotFoundException("id-" + id);

        EntityModel<Vendor> resource = EntityModel.of(vendor.get());

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllVendors());

        resource.add(linkTo.withRel("all-vendors"));

        return resource;
    }

    @DeleteMapping("/vendors/{id}")
    public void deleteVendor(@PathVariable long id) {
        vendorRepository.deleteById(id);
    }

    @PostMapping("/vendors")
    public ResponseEntity<Object> createVendor(@RequestBody Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedVendor.getVendorId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/vendors/{id}")
    public ResponseEntity<Object> updateVendor(@RequestBody Vendor vendor, @PathVariable long id) {

        Optional<Vendor> vendorOptional = vendorRepository.findById(id);

        if (!vendorOptional.isPresent())
            return ResponseEntity.notFound().build();

        vendor.setVendorId(id);

        vendorRepository.save(vendor);

        return ResponseEntity.noContent().build();
    }
}