package com.shifterwebapp.shifter;

import com.shifterwebapp.shifter.account.expert.Expert;
import com.shifterwebapp.shifter.account.expert.repository.ExpertRepository;
import javax.sql.DataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db-info")
    public ResponseEntity<?> getDatabaseInfo() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();

            return ResponseEntity.ok(Map.of(
                    "url", metaData.getURL(),
                    "username", metaData.getUserName(),
                    "database", conn.getCatalog(),
                    "schema", conn.getSchema()
            ));
        }
    }

    @GetMapping("/experts")
    public ResponseEntity<?> testExperts() {
        // Test 1: Count
        long count = expertRepository.count();
        System.out.println("Expert count: " + count);

        // Test 2: Find all
        List<Expert> all = expertRepository.findAll();
        System.out.println("All experts: " + all.size());
        all.forEach(e -> System.out.println("  Email: " + e.getEmail()));

        // Test 3: Find by email
        Optional<Expert> found = expertRepository.findByEmail("borjan2004@yahoo.com");
        System.out.println("Found by email: " + found.isPresent());

        // Test 4: Native query
        Query nativeQuery = entityManager.createNativeQuery(
                "SELECT * FROM expert WHERE email = 'borjan2004@yahoo.com'",
                Expert.class
        );
        List<Expert> nativeResults = nativeQuery.getResultList();
        System.out.println("Native query results: " + nativeResults.size());

        return ResponseEntity.ok(Map.of(
                "count", count,
                "allSize", all.size(),
                "foundByEmail", found.isPresent(),
                "nativeQuerySize", nativeResults.size()
        ));
    }
}