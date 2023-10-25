import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { TokenService } from '../token.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {


  loginData={
    email:'mkumari@gmail.com',
    password:'1234@'
  }
  constructor(private router: Router,private http: HttpClient,private tokenService: TokenService
    ) {}

    login() {
     
      // request to generate token
      this.tokenService.getToken(this.loginData.email,this.loginData.password).subscribe((data:any)=>{
        console.log('success');
        console.log(data);
        const token = data.token;

        // Store the token in a secure location (e.g., local storage)
        localStorage.setItem('token', token);

        // Redirect to the authenticated page (adjust the route as needed)
        this.router.navigateByUrl('api/v1/dashboard');

      },
      (error)=>{
        console.log("Error");
      });

    }

  
}


