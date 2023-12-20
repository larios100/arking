package com.example.arking.feature_login.domain.uses_cases

import android.util.Log
import com.example.arking.R
import com.example.arking.feature_login.domain.model.Credentials
import com.example.arking.feature_login.domain.repository.LoginRepository
import com.example.arking.utils.Resource
import com.example.arking.utils.TokenManager
import com.example.arking.utils.UiText

class ValidateCredentials constructor(
    private val loginRepository: LoginRepository,
    private val tokenManager: TokenManager
) {
    suspend operator fun invoke(userName: String, password: String): Resource<Unit> {
        try {
            var response = loginRepository.login(Credentials(userName,password))
            Log.i("API", response.toString())
            if(response.isSuccessful){
                Log.i("API", response.body().toString())
                val token = response.body()?.token
                if(token != null){
                    tokenManager.saveToken(token)
                    return Resource.Success(null);
                }
                return Resource.Error(UiText.StringResource(R.string.error_token_notfound));
            } else{
                if(response.code() == 400)
                    return Resource.Error(UiText.StringResource(R.string.error_invalid_credentials))
                if(response.code() == 403)
                    return Resource.Error(UiText.StringResource(R.string.error_forbidden))
                return Resource.Error(UiText.StringResource(R.string.error_500))
            }
        } catch (ex: Exception){
            Log.e("Api error",ex.toString())
            return Resource.Error(UiText.DynamicString(ex.toString()))
        }
    }
}