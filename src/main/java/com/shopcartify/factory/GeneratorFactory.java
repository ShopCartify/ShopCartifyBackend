package com.shopcartify.factory;

import com.shopcartify.model.Supermarket;
import com.shopcartify.repositories.SupermarketRepository;
import com.shopcartify.utils.Letter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Optional;

@AllArgsConstructor
@Component
public class GeneratorFactory {

    private static Character [] capitalLetters = Letter.getCapitalLetters();
    private static Character [] smallLetters = Letter.getSmallLetters();
    private static final SecureRandom random = new SecureRandom();
    @Autowired
    private SupermarketRepository supermarketRepository;


    public String generateSupermarketCode() {
        String generatedSupermarketCode = null;
        boolean isNotGenerated = true;
        do {
            generatedSupermarketCode = generateCode();
            Optional<Supermarket> foundSupermarket = supermarketRepository.findBySupermarketCode(generatedSupermarketCode);
            if(foundSupermarket.isEmpty())
                        isNotGenerated = false;
        }while(isNotGenerated);

        return generatedSupermarketCode;
    }

    public String generateCode() {
        StringBuilder randomString = new StringBuilder();

            randomString
                    .append(smallLetters[randomIndexGenerator()])
                    .append(capitalLetters[randomIndexGenerator()])
                    .append(randomNumberGenerator())
                    .append(smallLetters[randomIndexGenerator()])
                    .append(randomNumberGenerator());
            return randomString.toString();
        }
    private int randomIndexGenerator() {
      return random.nextInt(26);
    }
    private int randomNumberGenerator(){
            int randomNumber =  random.nextInt(9);
            return randomNumber;
    }


}
