/* eslint-disable */
import {UserModel} from "@/dto/UserModel";


export default function getAuthHeader(){
    const userModelString = localStorage.getItem('userModel');
    if(userModelString) {
        let userModel: UserModel = JSON.parse(userModelString);
        if (userModel.tokenResponseDto.token) {
            return {"Authorization": `Bearer ${userModel.tokenResponseDto.token}`};
        }
    }
    else {
        return {};
    }
}
