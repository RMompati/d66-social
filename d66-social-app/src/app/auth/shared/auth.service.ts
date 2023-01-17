import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SignupRequestPayload } from '../signup/singup.request.payload';
import { map, Observable } from 'rxjs';
import { D66Response } from '../response.payload';
import { LoginRequestPayload } from '../login/login.request.payload';
import { LocalStorageService } from 'ngx-webstorage';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl: string = "http://localhost:8080/api/auth";

  constructor(private httpClient: HttpClient, private localStorage: LocalStorageService) { }

  signup(signupRequestPayload: SignupRequestPayload): Observable<D66Response> {
    return this.httpClient.post<D66Response>(`${this.apiUrl}/signup`, signupRequestPayload);
  }

  login(loginRequestPayload: LoginRequestPayload): Observable<D66Response> {
    return this.httpClient.post<D66Response>(`${this.apiUrl}/login`, loginRequestPayload)
    .pipe<D66Response>(map<D66Response,D66Response>((data: D66Response) => {
      console.log(data);
      
      
      this.localStorage.store('auththenticationToken', data.data!.auth!.authenticationToken)
      this.localStorage.store('refreshToken', data.data!.auth!.refreshToken)
      this.localStorage.store('username', data.username)
      this.localStorage.store('expiresAt', data.data!.auth!.expiresAt)

      return data;
    }));
  }

  activate(token: string) {
    return this.httpClient.get<D66Response>(`${this.apiUrl}/activate-account/${token}`);
  }
}
