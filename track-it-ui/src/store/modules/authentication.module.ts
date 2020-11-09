/* eslint-disable */
import {Action, Module, Mutation, VuexModule} from "vuex-module-decorators";
import AuthService from "@/sevices/AuthService";
import {SignInDto} from "@/dto/SignInDto";
import {UserModel} from "@/dto/UserModel";
import router from "@/router";
const storedUser = localStorage.getItem("userModel");

@Module({namespaced: true})
class VuexUserAuth extends VuexModule {

    public isReLoginRequired = false;
    public loggedIn = storedUser ? true : false;
    public userModel : UserModel | null = storedUser ? JSON.parse(storedUser) : null;

    @Mutation
    public signInSuccess(user: any): void {
        this.loggedIn = true;
        this.isReLoginRequired = false;
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

    @Mutation
    public reLogin(){
        this.isReLoginRequired = true;
        this.loggedIn = false;
        this.userModel = null;
        router.push("/sign-in").catch(reason => console.debug(reason));
    }

    @Mutation
    public removeReLogin(){
        console.debug("Relogin removed");
        this.isReLoginRequired=false;
    }

    @Action({rawError: true})
    signIn(signInDto: SignInDto): Promise<any> {
        return AuthService.signIn(signInDto).then(
            user => {
                this.context.commit("signInSuccess", user);
                return Promise.resolve(user);
            },
            error => {
                this.context.commit("signInFailure");
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

    get isReLogin() : boolean {
        return this.isReLoginRequired;
    }
}

export default VuexUserAuth;
