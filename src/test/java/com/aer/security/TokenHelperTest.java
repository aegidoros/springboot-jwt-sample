package com.aer.security;


import com.aer.common.TimeProvider;
import com.aer.model.User;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Timestamp;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by fan.jin on 2017-01-08.
 */
public class TokenHelperTest {

    private static final String TEST_USERNAME = "testUser";

    @InjectMocks
    private TokenHelper tokenHelper;

    @Mock
    private LdapUserDetailsImpl userDetails;

    @Mock
    private TimeProvider timeProviderMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(tokenHelper, "EXPIRES_IN", 10); // 10 sec
        ReflectionTestUtils.setField(tokenHelper, "MOBILE_EXPIRES_IN", 20); // 20 sec
        ReflectionTestUtils.setField(tokenHelper, "SECRET", "mySecret");
    }

    @Test
    public void testGenerateTokenGeneratesDifferentTokensForDifferentCreationDates() throws Exception {
        when(timeProviderMock.now())
                .thenReturn(DateUtil.yesterday())
                .thenReturn(DateUtil.now());

        final String token = createToken();
        final String laterToken = createToken();

        assertThat(token).isNotEqualTo(laterToken);
    }

    @Test
    public void mobileTokenShouldLiveLonger() {
        Date beforeSomeTime = new Date(DateUtil.now().getTime() - 15 * 1000);

        LdapUserDetails userDetails = mock(LdapUserDetailsImpl.class);
        when(userDetails.getUsername()).thenReturn(TEST_USERNAME);

        when(timeProviderMock.now())
                .thenReturn(beforeSomeTime);
        final String mobileToken = createToken();
        assertThat(tokenHelper.validateToken(mobileToken, userDetails)).isTrue();
    }

    @Test
    public void mobileTokenShouldExpire() {
        Date beforeSomeTime = new Date(DateUtil.now().getTime() - 20 * 1000);

        when(timeProviderMock.now())
                .thenReturn(beforeSomeTime);

        LdapUserDetails userDetails = mock(LdapUserDetailsImpl.class);
        when(userDetails.getUsername()).thenReturn(TEST_USERNAME);

        final String mobileToken = createToken();
        assertThat(tokenHelper.validateToken(mobileToken, userDetails)).isFalse();
    }

    @Test
    public void getUsernameFromToken() throws Exception {
        when(timeProviderMock.now()).thenReturn(DateUtil.now());

        final String token = createToken();

        assertThat(tokenHelper.getUsernameFromToken(token)).isEqualTo(TEST_USERNAME);
    }

    @Test
    public void getCreatedDateFromToken() {
        final Date now = DateUtil.now();
        when(timeProviderMock.now()).thenReturn(now);

        final String token = createToken();

        assertThat(tokenHelper.getIssuedAtDateFromToken(token)).isInSameMinuteWindowAs(now);
    }

    @Test
    public void expiredTokenCannotBeRefreshed() {
        when(timeProviderMock.now())
                .thenReturn(DateUtil.yesterday());

        String token = createToken();
        tokenHelper.refreshToken(token);
    }


    @Test
    public void changedPasswordCannotBeRefreshed() throws Exception {
        when(timeProviderMock.now())
                .thenReturn(DateUtil.now());

        LdapUserDetailsImpl User = mock(LdapUserDetailsImpl.class);
        String token = createToken();
        assertThat(tokenHelper.validateToken(token, User)).isFalse();
    }

    @Test
    public void canRefreshToken() throws Exception {
        when(timeProviderMock.now())
                .thenReturn(DateUtil.now())
                .thenReturn(DateUtil.tomorrow());
        String firstToken = createToken();
        String refreshedToken = tokenHelper.refreshToken(firstToken);
        Date firstTokenDate = tokenHelper.getIssuedAtDateFromToken(firstToken);
        Date refreshedTokenDate = tokenHelper.getIssuedAtDateFromToken(refreshedToken);
        assertThat(firstTokenDate).isBefore(refreshedTokenDate);
    }

    private String createToken() {
        return tokenHelper.generateToken(userDetails);
    }

}
