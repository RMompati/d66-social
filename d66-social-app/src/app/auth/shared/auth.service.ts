import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SignupRequestPayload } from '../signup/singup.request.payload';
import { Observable } from 'rxjs';
import { D66Response } from '../response.payload';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl: string = "http://localhost:8080/api/auth";

  constructor(private httpClient: HttpClient) { }

  signup(signupRequestPayload: SignupRequestPayload): Observable<D66Response> {
    return this.httpClient.post<D66Response>(`${this.apiUrl}/signup`, signupRequestPayload);
  }
}
