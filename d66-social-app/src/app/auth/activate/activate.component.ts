import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FlashMessageService } from 'src/app/message/flash-message.service';
import { D66Response, emptyResponse } from '../response.payload';
import { AuthService } from '../shared/auth.service';

@Component({
  selector: 'app-activate',
  template: ''
})
export class ActivateComponent implements OnInit {

  private token!: string;

  constructor(private router: Router, private activatedRoute: ActivatedRoute,
    private flashMessage: FlashMessageService, private authService: AuthService) { }

  ngOnInit(): void {

    // Extract the token
    this.activatedRoute.paramMap.subscribe(paramMap => {
      this.token = paramMap.get('token')!;
    });
    console.log(this.token);
    
    
    // Send an activation api request
    this.authService.activate(this.token).subscribe(d66Response => {
      // Extract the message
      this.flashMessage.setMessage(d66Response.message)
      this.flashMessage.setMessageType(d66Response.statusCode);
      console.log(d66Response);
      
    });
    // Redirect to login page.
    this.router.navigate(['/login']);
  }

}
