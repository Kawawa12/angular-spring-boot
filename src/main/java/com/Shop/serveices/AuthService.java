package com.Shop.serveices;

import com.Shop.dto.SignIn;
import com.Shop.dto.SignUp;
import com.Shop.response.Response;

public interface AuthService {

    Response signUp(SignUp signUpReq);

    Response signIn(SignIn signIn);

}
