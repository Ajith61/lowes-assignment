package com.lowes.productservice.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.lowes.productservice.model.dao.ProductDao;
import com.lowes.productservice.model.dto.ProductDto;
import com.lowes.productservice.repository.ProductRepository;
import com.lowes.productservice.util.constants.ApplicationStatus;
import com.lowes.productservice.util.model.ResponseModel;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRespository;
    @Autowired
    private  StreamBridge streamBridge;


	public ResponseModel<ProductDto> createProduct(@Valid ProductDto newProductDto) {
		try {
			ProductDao productDao =  new ProductDao();
			BeanUtils.copyProperties(newProductDto, productDao);
			//check data already exists
			ProductDao resultProductDao = productRespository.findProductByProductId(productDao.getProductId());
			
			if (resultProductDao!=null) {
				return new ResponseModel<ProductDto>(ApplicationStatus.PRODUCT_ALREADY_REGISTERED.getStatusCode(),
						ApplicationStatus.PRODUCT_ALREADY_REGISTERED.getStatusMessage(), new ProductDto());
			} else {
				ProductDao insertedProductDao = productRespository.save(productDao);
				if (insertedProductDao!=null) {
					ProductDao eventData = new ProductDao();
					eventData.setProductId(insertedProductDao.getProductId());
					eventData.setProductStock(insertedProductDao.getProductStock());
					//inventoryClient.saveStock(insertedProductDao.getProductId(), insertedProductDao.getProductStock());
					streamBridge.send("notificationEventSupplier-out-0", MessageBuilder.withPayload(eventData).build());
					return new ResponseModel<ProductDto>(ApplicationStatus.CREATED.getStatusCode(),
							ApplicationStatus.CREATED.getStatusMessage(), null);
								
				} 
				else {
					return new ResponseModel<ProductDto>(ApplicationStatus.ERROR.getStatusCode(),
							ApplicationStatus.ERROR.getStatusMessage(), null);
				}
			}
		} catch (Exception ex) {
			return new ResponseModel<ProductDto>(ApplicationStatus.ERROR.getStatusCode(), ex.getLocalizedMessage(),
					null);
		}
	}

	public ResponseModel<List<ProductDto>> getAllProducts() {
		try {
			// get all product
			List<ProductDao> productLists = productRespository.findAll();
			if (!CollectionUtils.isEmpty(productLists)) {
				
				 List<ProductDto> convertedProductLists = productLists.stream()
                         .map(product -> new ProductDto( product.getProductPrice(),product.getProductName(),product.getId(),product.getProductStock()))
                         .collect(Collectors.toList());
				
				
				return new ResponseModel<List<ProductDto>>(ApplicationStatus.VALID_RESPONSE.getStatusCode(),
						ApplicationStatus.VALID_RESPONSE.getStatusMessage(), convertedProductLists);
			} else {
					return new ResponseModel<List<ProductDto>>(ApplicationStatus.NO_DATA.getStatusCode(),
							ApplicationStatus.NO_DATA.getStatusMessage(), null);
				}
		} catch (Exception ex) {
			return new ResponseModel<List<ProductDto>>(ApplicationStatus.ERROR.getStatusCode(), ex.getLocalizedMessage(),
					null);
		}
	}

}
