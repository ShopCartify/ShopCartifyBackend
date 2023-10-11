package com.shopcartify.services.implementations;

import com.shopcartify.dto.reqests.NewProductRequest;
import com.shopcartify.dto.reqests.SupermarketRegistrationRequest;
import com.shopcartify.dto.responses.NewProductResponse;
import com.shopcartify.dto.responses.SupermarketRegistrationResponse;
import com.shopcartify.enums.UserRole;
import com.shopcartify.exceptions.ProductNotFoundException;
import com.shopcartify.exceptions.SupermarketNotFoundException;
import com.shopcartify.factory.GeneratorFactory;
import com.shopcartify.mailSender.EmailService;
import com.shopcartify.model.Admin;
import com.shopcartify.model.Product;
import com.shopcartify.model.Supermarket;
import com.shopcartify.repositories.AdminRepository;
import com.shopcartify.repositories.SupermarketRepository;
import com.shopcartify.services.interfaces.ProductService;
import com.shopcartify.services.interfaces.SupermarketService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.shopcartify.enums.ExceptionMessage.SUPERMARKET_NOT_FOUND;


@Service
@AllArgsConstructor

public class ShopCartifySupermarketService implements SupermarketService {
    private final ModelMapper mapper = new ModelMapper();
    private final GeneratorFactory generatorFactory ;
    private final ProductService productService;
    private final EmailService emailService;

    private final AdminRepository adminRepository;

    private final SupermarketRepository supermarketRepository;
    @Override
    public SupermarketRegistrationResponse registerNewSupermarket(SupermarketRegistrationRequest supermarketRegistrationRequest) {
        Supermarket supermarket = mapper.map(supermarketRegistrationRequest, Supermarket.class);
        String supermarketCode = generatorFactory.generateSupermarketCode();

        supermarket.setSupermarketCode(supermarketCode);
        supermarket.setAdmins(new HashSet<>());
        Supermarket savedSupermarket = supermarketRepository.save(supermarket);


        return SupermarketRegistrationResponse.builder()
                .supermarketCode(savedSupermarket.getSupermarketCode())
                .supermarketName(savedSupermarket.getSupermarketName())
                .supermarketEmail(savedSupermarket.getSupermarketEmail())
                .message("Supermarket Saved Successfully. You are an admin")
                .build();
    }

    @Override

    public NewProductResponse addNewProduct(NewProductRequest productRequest) {

        return productService.addNewProduct(productRequest);
    }


    @Override
    public Supermarket findSupermarketBySupermarketCode(String supermarketCode) {
       return supermarketRepository.findBySupermarketCode(supermarketCode)
                                .orElseThrow(()-> new SupermarketNotFoundException(SUPERMARKET_NOT_FOUND));

    }
    @Override
    public Product findProductByToken(String token) {

        return productService.findProductByToken(token);
    }

    @Override
    public Product findProductByNameAndSupermarketCode(String productName) {
        return null;
    }

    public Supermarket findProductById(Long id) {
        return supermarketRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not found"));
    }
    public Supermarket findBySuperMarketCode(String code){
        return supermarketRepository.findBySupermarketCode(code).orElseThrow(()-> new ProductNotFoundException("Product not found"));
    }

    @Override
    public List<Supermarket> findAllSupermarket(List<Long> id) {
        return supermarketRepository.findAll();
    }

    @Override
    public Supermarket update(Supermarket supermarket) {
        return supermarketRepository.save(supermarket);
    }

    @Override
    public Admin inviteAdminToSupermarket(String supermarketCode, String adminEmail, UserRole adminRole) {
        Supermarket supermarket = findSupermarketBySupermarketCode(supermarketCode);
        Admin existingAdmin = adminRepository.findByAdminEmail(adminEmail);

        if (existingAdmin != null) {
            existingAdmin.setSupermarket(supermarket);
            existingAdmin.getRoles().add(adminRole);
            adminRepository.save(existingAdmin);

            supermarket.getAdmins().add(existingAdmin);
            supermarketRepository.save(supermarket);

            sendInvitationEmail(adminEmail, supermarket.getSupermarketName(), adminRole);

            return existingAdmin;
        } else {
            Admin admin = new Admin();
            admin.setAdminEmail(adminEmail);
            admin.setSupermarket(supermarket);

            admin.setRoles(Collections.singleton(adminRole));

            Admin savedAdmin = adminRepository.save(admin);

            supermarket.getAdmins().add(savedAdmin);
            supermarketRepository.save(supermarket);

            sendInvitationEmail(adminEmail, supermarket.getSupermarketName(), adminRole);

            return savedAdmin;
        }
    }

    public void sendInvitationEmail(String adminEmail, String supermarketName, UserRole adminRole) {
        String subject = "Invitation to Supermarket " + supermarketName;
        String body = "You have been invited to join Supermarket " + supermarketName + " as a " + adminRole.name() + " Admin.";

        emailService.sendEmail(adminEmail, subject, body);
    }
}
