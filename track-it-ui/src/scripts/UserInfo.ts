/* eslint-disable */
class UserInfo {
    constructor(userId: string, email: string, token: string) {
        this.userId = userId;
        this.email = email;
        this.token =token;
    }

    token: string;
    userId: string;
    email:string;
}

export default new UserInfo("test","test","test");
