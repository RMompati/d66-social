import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { AuthService } from "./auth/shared/auth.service";

@Injectable({
  providedIn: 'root'
})
export class D66HttpInterceptor implements HttpInterceptor {

  isTokenRefreshing = false;
  refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);

  constructor(public authService: AuthService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const jwToken = this.authService.getJwToken();
    
    if (jwToken) {
      request = this.addToken(request, jwToken);
    }
    return next.handle(request);
  }
  
  addToken(request: HttpRequest<any>, jwToken: string) {
    return request.clone({
      headers: request.headers.set('Authorization', `Bearer ${jwToken}`)
    });
  }

  private handleAuthErrors(request: HttpRequest<any>, next: HttpHandler) {

    if (!this.isTokenRefreshing) {
      this.isTokenRefreshing = true;
      this.refreshTokenSubject.next(null);
      
    }
  }
} 