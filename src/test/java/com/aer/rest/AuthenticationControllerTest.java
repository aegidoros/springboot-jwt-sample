//package com.aer.rest;
//
//import com.aer.common.TimeProvider;
//import com.aer.entities.RoleEntity;
//import com.aer.model.Permission;
//import com.aer.model.User;
//import com.aer.security.TokenHelper;
//import org.assertj.core.util.DateUtil;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.util.ReflectionTestUtils;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//
///**
// * Created by fanjin on 2017-09-01.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AuthenticationControllerTest {
//
//    private MockMvc mvc;
//
//    @MockBean
//    private TimeProvider timeProviderMock;
//    @MockBean
//    private User userDetails;
//
//    @Autowired
//    private TokenHelper tokenHelper;
//
//    @InjectMocks
//    private AuthenticationController authenticationController;
//
//    @Autowired
//    private WebApplicationContext context;
//
//
//    @Before
//    public void setup() {
//
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//
//        User user = new User();
//        user.setUsername("username");
//        RoleEntity role = new RoleEntity();
//        role.setId(0L);
//        role.setName("ROLE_USER");
//        List<Permission> authorities =new ArrayList<>();
//        authorities.add(new Permission());
//        user.setAuthorities(authorities);
//        //when(this.userDetailsService.loadUserByUsername(eq("testUser"))).thenReturn(user);
//        MockitoAnnotations.initMocks(this);
//
//        ReflectionTestUtils.setField(tokenHelper, "EXPIRES_IN", 100); // 100 sec
//        ReflectionTestUtils.setField(tokenHelper, "MOBILE_EXPIRES_IN", 200); // 200 sec
//        ReflectionTestUtils.setField(tokenHelper, "SECRET", "queenvictoria");
//
//    }
//
//    @Test
//    public void shouldGetEmptyTokenStateWhenGivenValidOldToken() throws Exception {
//        when(timeProviderMock.now())
//                .thenReturn(DateUtil.yesterday());
//        this.mvc.perform(post("/auth/refresh")
//                .header("Authorization", "Bearer 123"))
//                .andExpect(content().json("{access_token:null,expires_in:null}"));
//    }
//
//    @Test
//    @WithMockUser(roles = "USER")
//    public void shouldRefreshNotExpiredWebToken() throws Exception {
//
//        given(timeProviderMock.now())
//                .willReturn(new Date(30L));
//
//        String token = createToken();
//        String refreshedToken = tokenHelper.refreshToken(token );
//
//        this.mvc.perform(post("/auth/refresh")
//                .header("Authorization", "Bearer " + token))
//                .andExpect(content().json("{access_token:" + refreshedToken + ",expires_in:100}"));
//    }
//
//    @Test
//    @WithMockUser(roles = "USER")
//    public void shouldRefreshNotExpiredMobileToken() throws Exception {
//        given(timeProviderMock.now())
//                .willReturn(new Date(30L));
//        String token = createToken();
//        String refreshedToken = tokenHelper.refreshToken(token);
//
//        this.mvc.perform(post("/auth/refresh")
//                .header("Authorization", "Bearer " + token))
//                .andExpect(content().json("{access_token:" + refreshedToken + ",expires_in:200}"));
//    }
//
//    @Test
//    public void shouldNotRefreshExpiredWebToken() throws Exception {
//        Date beforeSomeTime = new Date(DateUtil.now().getTime() - 15 * 1000);
//        when(timeProviderMock.now())
//                .thenReturn(beforeSomeTime);
//        String token = createToken();
//        this.mvc.perform(post("/auth/refresh")
//                .header("Authorization", "Bearer " + token))
//                .andExpect(content().json("{access_token:null,expires_in:null}"));
//    }
//
//    @Test
//    public void shouldRefreshExpiredMobileToken() throws Exception {
//        Date beforeSomeTime = new Date(DateUtil.now().getTime() - 15 * 1000);
//        when(timeProviderMock.now())
//                .thenReturn(beforeSomeTime);
//        String token = createToken();
//        this.mvc.perform(post("/auth/refresh").header("Authorization", "Bearer " + token))
//                .andExpect(content().json("{access_token:null,expires_in:null}"));
//    }
//
//    private String createToken() {
//        return tokenHelper.generateToken(userDetails);
//    }
//}
