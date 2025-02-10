//package com.example.TenantAndProperties.service;
//
//import com.example.TenantAndProperties.model.Tenant;
//import com.example.TenantAndProperties.repository.TenantRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static com.example.TenantAndProperties.service.TenantTestData.testTenant;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//class TenantServiceTest {
//
//    @Mock
//    private TenantRepository tenantRepository;
//
//    @InjectMocks
//    private TenantService tenantTest;
//
//
//    @Test
//    public void testThatTenantIsAdded() {
//
//        final Tenant tenant = testTenant();
//
//        when(tenantRepository.save(any(Tenant.class))).thenReturn(tenant);
//
//        final Tenant result = tenantTest.addTenant(tenant);
//
//        assertNotNull(result, "Returned tenant should not be null");
//        assertEquals(tenant, result);
//
//
//    }
//}