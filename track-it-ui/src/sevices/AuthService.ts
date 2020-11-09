/* eslint-disable */
import http from "../http-commons";
import {SignInDto} from "@/dto/SignInDto";
import {UserModel} from "@/dto/UserModel";
import {TokenResponseDto} from "@/dto/TokenResponseDto";
import store from "@/store";

class AuthService {


    signIn(signInDto: SignInDto) {
        return http.post("/login", signInDto).then(
            response => {
                const tokenResponseDto: TokenResponseDto = response.data;
                const userModel: UserModel = {tokenResponseDto: tokenResponseDto, email: signInDto.email};
                localStorage.setItem("userModel", JSON.stringify(userModel));
                return userModel;
            }
        )
    }

    signOut() {
        localStorage.removeItem("userModel");
    }

    triggerReLoginMutation(){
        this.signOut()
        store.commit("Authentication/reLogin");
    }

}

export default new AuthService();
