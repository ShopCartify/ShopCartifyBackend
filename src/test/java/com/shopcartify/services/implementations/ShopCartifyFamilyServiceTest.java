//package com.shopcartify.services.implementations;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.HashSet;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class ShopCartifyFamilyServiceTest {
//
//    @Mock
//    private ShopCartifyFamilyRepository familyRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private ShopCartifyFamilyService familyService;
//
//    @Test
//    public void testCreateFamily() {
//        FamilyAccountRequest request = new FamilyAccountRequest();
//        request.setFamilyName("NedStack");
//        request.setPassword("password123");
//
//        Family mockFamily = new Family();
//        mockFamily.setFamilyName(request.getFamilyName());
//
//        when(passwordEncoder.encode(request.getPassword())).thenReturn("password");
//        when(familyRepository.save(any(Family.class))).thenReturn(mockFamily);
//
//        Family createdFamily = familyService.createFamily(request);
//
//        assertEquals(request.getFamilyName(), createdFamily.getFamilyName());
//    }
//
//    @Test
//    public void testAddMemberToFamilyAccount_UserNotFound() {
//        Long ownerId = 1L;
//        String memberUsername = "nedsFamily";
//
//        when(familyRepository.findById(ownerId)).thenReturn(Optional.empty());
//
//        assertThrows(OwnerNotFoundException.class, () -> {
//            familyService.addMemberToFamilyAccount(ownerId, memberUsername);
//        });
//    }
//
//    @Test
//    public void testAddMemberToFamilyAccount() {
//        Long ownerId = 1L;
//        String memberUsername = "NewMember";
//
//        Family owner = new Family();
//        owner.setId(ownerId);
//        owner.setFamilyMembers(new HashSet<>());
//
//        ShopCartifyUser member = new ShopCartifyUser();
//        member.setUserName(memberUsername);
//
//        when(familyRepository.findById(eq(ownerId))).thenReturn(Optional.of(owner));
//        when(familyRepository.findByUserName(eq(memberUsername))).thenReturn(Optional.of(owner));
//        when(familyRepository.save(owner)).thenReturn(owner);
//
//        Family updatedFamily = familyService.addMemberToFamilyAccount(ownerId, memberUsername);
//
//        assertEquals(1, updatedFamily.getFamilyMembers().size());
//        verify(familyRepository, times(1)).findById(eq(ownerId));
//        verify(familyRepository, times(1)).findByUserName(eq(memberUsername));
//        verify(familyRepository, times(1)).save(owner);
//    }
//
//
//    @Test
//    public void testRemoveMemberFromFamilyAccount() {
//        Long ownerId = 1L;
//        Long memberId = 2L;
//
//        Family owner = new Family();
//        owner.setId(ownerId);
//        owner.setFamilyMembers(new HashSet<>());
//
//        ShopCartifyUser member = new ShopCartifyUser();
//        member.setUserId(memberId);
//
//        owner.getFamilyMembers().add(member);
//
//        when(familyRepository.findById(eq(ownerId))).thenReturn(Optional.of(owner));
//        when(familyRepository.findShopCartifyUserById(eq(memberId))).thenReturn(Optional.of(member));
//        when(familyRepository.save(owner)).thenReturn(owner);
//
//        ShopCartifyUser removedMember = familyService.removeMemberFromFamilyAccount(ownerId, memberId);
//
//        assertEquals(0, owner.getFamilyMembers().size());
//        assertEquals(member, removedMember);
//        verify(familyRepository, times(1)).findById(eq(ownerId));
//        verify(familyRepository, times(1)).findShopCartifyUserById(eq(memberId));
//        verify(familyRepository, times(1)).save(owner);
//    }
//
//
//    @Test
//    public void testRemoveMemberFromFamilyAccount_MemberNotFound() {
//        Long ownerId = 1L;
//        Long memberId = 2L;
//
//        when(familyRepository.findById(ownerId)).thenReturn(Optional.empty());
//        when(familyRepository.findShopCartifyUserById(memberId)).thenReturn(Optional.empty());
//
//        assertThrows(OwnerNotFoundException.class, () -> {
//            familyService.removeMemberFromFamilyAccount(ownerId, memberId);
//        });
//    }
//
//}