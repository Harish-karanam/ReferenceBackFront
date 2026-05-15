package com.example.demo.SecurityConfigurations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.Enums.DealStage;
import com.example.demo.Enums.DealType;
import com.example.demo.Enums.Role;
import com.example.demo.entity.Deal;
import com.example.demo.entity.Note;
import com.example.demo.entity.User;
import com.example.demo.repository.DealRepository;
import com.example.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DealRepository dealRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing database with sample data...");
        
        // Clear existing data (optional - for development only)
        // dealRepository.deleteAll();
        // userRepository.deleteAll();
        
        // Create users if they don't exist
        createUsers();
        
        // Create deals if they don't exist
        createDeals();
        
        System.out.println("Database initialization completed!");
    }
    
    private void createUsers() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@bank.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setActive(true);
            userRepository.save(admin);
            System.out.println("✓ Admin user created: admin / admin123");
        }
        
        if (!userRepository.existsByUsername("john.doe")) {
            User john = new User();
            john.setUsername("john.doe");
            john.setEmail("john@bank.com");
            john.setPassword(passwordEncoder.encode("user123"));
            john.setRole(Role.USER);
            john.setActive(true);
            userRepository.save(john);
            System.out.println("✓ User created: john.doe / user123");
        }
        
        if (!userRepository.existsByUsername("jane.smith")) {
            User jane = new User();
            jane.setUsername("jane.smith");
            jane.setEmail("jane@bank.com");
            jane.setPassword(passwordEncoder.encode("password"));
            jane.setRole(Role.USER);
            jane.setActive(true);
            userRepository.save(jane);
            System.out.println("✓ User created: jane.smith / password");
        }
        
        if (!userRepository.existsByUsername("akhi")) {
            User akhi = new User();
            akhi.setUsername("akhi");
            akhi.setEmail("akhi@gmail.com");
            akhi.setPassword(passwordEncoder.encode("akhi@123"));
            akhi.setRole(Role.USER);
            akhi.setActive(true);
			userRepository.save(akhi);
            System.out.println("✓ User created: akhi / akhi@123");
        }
        
        if (!userRepository.existsByUsername("bob.wilson")) {
            User bob = new User();
            bob.setUsername("bob.wilson");
            bob.setEmail("bob@bank.com");
            bob.setPassword(passwordEncoder.encode("password"));
            bob.setRole(Role.ADMIN);
            bob.setActive(true);
            userRepository.save(bob);
            System.out.println("✓ Admin created: bob.wilson / password");
        }
        
        if (!userRepository.existsByUsername("sarah.jones")) {
            User sarah = new User();
            sarah.setUsername("sarah.jones");
            sarah.setEmail("sarah@bank.com");
            sarah.setPassword(passwordEncoder.encode("password"));
            sarah.setRole(Role.USER);
            sarah.setActive(false); // Inactive user
            userRepository.save(sarah);
            System.out.println("✓ Inactive user created: sarah.jones / password");
        }
    }
    
    private void createDeals() {
        if (dealRepository.count() == 0) {
            // Get users
            User admin = userRepository.findByUsername("admin")
                .orElseThrow(() -> new RuntimeException("Admin user not found"));
            User john = userRepository.findByUsername("john.doe")
                .orElseThrow(() -> new RuntimeException("John user not found"));
            User jane = userRepository.findByUsername("jane.smith")
                .orElseThrow(() -> new RuntimeException("Jane user not found"));
            User bob = userRepository.findByUsername("bob.wilson")
                .orElseThrow(() -> new RuntimeException("Bob user not found"));
            
            // Create sample deals
            Deal deal1 = new Deal();
            deal1.setClientName("Acme Corporation");
            deal1.setDealType(DealType.M_A);
            deal1.setSector("Technology");
            deal1.setDealValue(50000000.00);
            deal1.setCurrentStage(DealStage.PROSPECT);
            deal1.setSummary("Potential acquisition of tech startup specializing in AI solutions");
            deal1.setCreatedBy(admin);
            deal1.setAssignedTo(john);
            deal1.setCreatedAt(LocalDateTime.now().minusDays(10));
            
            Deal deal2 = new Deal();
            deal2.setClientName("Global Finance Ltd");
            deal2.setDealType(DealType.EQUITY_FINANCING);
            deal2.setSector("Finance");
            deal2.setDealValue(75000000.00);
            deal2.setCurrentStage(DealStage.UNDER_EVALUATION);
            deal2.setSummary("Series C funding round for fintech expansion in APAC region");
            deal2.setCreatedBy(john);
            deal2.setAssignedTo(jane);
            deal2.setCreatedAt(LocalDateTime.now().minusDays(7));
            
            Deal deal3 = new Deal();
            deal3.setClientName("Green Energy Inc");
            deal3.setDealType(DealType.IPO);
            deal3.setSector("Energy");
            deal3.setDealValue(150000000.00);
            deal3.setCurrentStage(DealStage.TERM_SHEET_SUBMITTED);
            deal3.setSummary("Initial public offering preparation for renewable energy company");
            deal3.setCreatedBy(admin);
            deal3.setAssignedTo(bob);
            deal3.setCreatedAt(LocalDateTime.now().minusDays(5));
            
            Deal deal4 = new Deal();
            deal4.setClientName("MediCare Solutions");
            deal4.setDealType(DealType.DEBT_OFFERING);
            deal4.setSector("Healthcare");
            deal4.setDealValue(25000000.00);
            deal4.setCurrentStage(DealStage.CLOSED);
            deal4.setSummary("Corporate bond issuance completed for hospital network expansion");
            deal4.setCreatedBy(jane);
            deal4.setAssignedTo(john);
            deal4.setCreatedAt(LocalDateTime.now().minusDays(30));
            
            Deal deal5 = new Deal();
            deal5.setClientName("AutoTech Motors");
            deal5.setDealType(DealType.M_A);
            deal5.setSector("Automotive");
            deal5.setDealValue(120000000.00);
            deal5.setCurrentStage(DealStage.LOST);
            deal5.setSummary("Merger deal fell through due to regulatory concerns");
            deal5.setCreatedBy(bob);
            deal5.setAssignedTo(jane);
            deal5.setCreatedAt(LocalDateTime.now().minusDays(45));
            
            Deal deal6 = new Deal();
            deal6.setClientName("Urban Development Co");
            deal6.setDealType(DealType.EQUITY_FINANCING);
            deal6.setSector("Real Estate");
            deal6.setDealValue(45000000.00);
            deal6.setCurrentStage(DealStage.PROSPECT);
            deal6.setSummary("Real estate development funding for smart city project");
            deal6.setCreatedBy(admin);
            deal6.setAssignedTo(john);
            deal6.setCreatedAt(LocalDateTime.now().minusDays(3));
            
            Deal deal7 = new Deal();
            deal7.setClientName("FoodChain Logistics");
            deal7.setDealType(DealType.DEBT_OFFERING);
            deal7.setSector("Logistics");
            deal7.setDealValue(18000000.00);
            deal7.setCurrentStage(DealStage.UNDER_EVALUATION);
            deal7.setSummary("Supply chain financing for cold storage expansion");
            deal7.setCreatedBy(jane);
            deal7.setAssignedTo(bob);
            deal7.setCreatedAt(LocalDateTime.now().minusDays(2));
            
            // Save all deals
            dealRepository.saveAll(Arrays.asList(deal1, deal2, deal3, deal4, deal5, deal6, deal7));
            
            // Add notes to some deals
            addSampleNotes(deal1, admin, john);
            addSampleNotes(deal2, john, jane);
            
            System.out.println("✓ 7 sample deals created with notes");
        }
    }
    
    private void addSampleNotes(Deal deal, User... users) {
        Note note1 = new Note();
        note1.setNote("Initial contact made with client. Positive response received.");
        note1.setUser(users[0]);
        note1.setTimestamp(LocalDateTime.now().minusDays(2));
        
        Note note2 = new Note();
        note2.setNote("Scheduled due diligence meeting for next week.");
        note2.setUser(users.length > 1 ? users[1] : users[0]);
        note2.setTimestamp(LocalDateTime.now().minusDays(1));
        
        Note note3 = new Note();
        note3.setNote("Financial analysis completed. Preparing preliminary report.");
        note3.setUser(users[0]);
        note3.setTimestamp(LocalDateTime.now());
        
        deal.setNotes(Arrays.asList(note1, note2, note3));
        dealRepository.save(deal);
    }
}