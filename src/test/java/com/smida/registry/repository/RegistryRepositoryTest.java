package com.smida.registry.repository;

import com.smida.registry.domain.Registry;
import com.smida.registry.domain.RegistryStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Sql(statements = {"delete from registry"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RegistryRepositoryTest {

    @Autowired
    private RegistryRepository registryRepository;

    @Test
    public void testFindByUsreou() {
        Registry r1 = createRegistry("111111111");
        createRegistry("129999999");
        createRegistry("177777777");

        Registry actualResult = registryRepository.findByUsreou(r1.getUsreou());

        Assert.assertEquals(r1.getId(), actualResult.getId());
    }

    private Registry createRegistry(String usreou) {
        Registry registry = new Registry();
        registry.setDateOfIssue(LocalDate.of(2000, 10, 20));
        registry.setUsreou(usreou);
        registry.setComment("some text");
        registry.setStatus(RegistryStatus.ACTIVE);
        registry.setQuantity(20.0);
        registry.setNominalValue(10.0);
        registry.setTotalValue(200.0);
        return registryRepository.save(registry);
    }
}
