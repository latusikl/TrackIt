/* eslint-disable */
import {Action, Module, Mutation, VuexModule} from "vuex-module-decorators";
import AuthService from "@/sevices/AuthService";
import {SignInDto} from "@/dto/SignInDto";
import {UserModel} from "@/dto/UserModel";

const storedUser = localStorage.getItem("userModel");

@Module({namespaced: true})
class VuexUserAuth extends VuexModule {

    public loggedIn = storedUser ? true : false;
    public userModel : UserModel | null = storedUser ? JSON.parse(storedUser) : null;

    @Mutation
    public signInSuccess(user: any): void {
        this.loggedIn = true;
        console.log(user)
        this.userModel= user;
    }

    @Mutation
    public signInFailure(): void {
        this.loggedIn = false;
        this.userModel = null;
    }


    @Mutation
    public signOutMutation(): void {
        this.loggedIn = false;
        this.userModel = null;
    }


    @Action({rawError: true})
    signIn(signInDto: SignInDto): Promise<any> {
        return AuthService.signIn(signInDto).then(
            user => {
                console.warn(user);
                this.context.commit('signInSuccess', user.data);
                return Promise.resolve(user.data);
            },
            error => {
                this.context.commit('signInFailure');
                return Promise.reject(error);
            }
        );
    }

    @Action
    signOut(): void {
        AuthService.signOut();
        this.context.commit('signOutMutation');
    }

    get isLogged(): boolean {
        return this.loggedIn;
    }

    get user() : UserModel | null{
        return this.userModel
    }
}

export default VuexUserAuth;
