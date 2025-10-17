package com.company.brokage.repository;

import com.company.brokage.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AssetRepository extends JpaRepository<Asset, String> {

    ArrayList<Asset> findByCustomerId(String customerId);

    Asset findByCustomerIdAndAssetName(String customerId, String assetName);


}
