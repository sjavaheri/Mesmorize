import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Message } from "@common/message";
import { Observable, firstValueFrom, of } from "rxjs";
import { catchError } from "rxjs/operators";
import { environment } from "src/environments/environment";
import jwt_decode from "jwt-decode";
import { existsSync } from "fs";

/**
 * Class to send Http Requests to the backend with the JWT token taken as a cookie
 * @author Shidan Javaheri
 */
@Injectable({
  providedIn: "root",
})
export class CommunicationService {
  // url of the server
  private readonly baseUrl: string = environment.serverUrl;

  // initialize the http client to send requests
  constructor(private readonly http: HttpClient) {}

  /**
   * Method to send a GET request with the JWT token
   * @returns
   * @author Shidan Javaheri + credit ChatGPT
   */
  basicGet(url: String): Observable<Message> {
    // get the token and create the headers
    const token = this.getTokenCookie();
    const headers = this.createHeaders(token);
    return this.http
      .get<Message>(`${this.baseUrl}` + url, { headers })
      .pipe(catchError(this.handleError<Message>("basicGet")));
  }

  /**
   * Method to send a POST request with JWT token
   * @param message
   * @param url
   * @returns
   * @author Shidan Javaheri + credit ChatGPT
   */
  basicPost(url: String, message: Message): Observable<HttpResponse<string>> {
    // get the token and create the headers
    const token = this.getTokenCookie();
    const headers = this.createHeaders(token);
    return this.http.post(`${this.baseUrl}` + url, message, {
      headers,
      observe: "response",
      responseType: "text",
    });
  }

  /**
   * Function to handle error messages. Needs adjustment
   * @param request
   * @param result
   * @returns
   * @author Shidan Javaheri
   */
  private handleError<T>(
    request: string,
    result?: T
  ): (error: Error) => Observable<T> {
    return () => of(result as T);
  }

  /**
   * Function to set or delete the token as a cookie
   * Deletes the cookie if the input is an empty string
   * @param token
   * @author Shidan Javaheri + credit ChatGPT + ECSE 428 Group Project
   */
  private setTokenCookie(token: string): void {
    // if the token is null, the cookie is deleted
    if (token === "") {
      document.cookie =
        "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    } else {
      // create the cookie
      const decoded = jwt_decode(token) as { exp: number };
      const tokenExpiry = decoded.exp; // in seconds
      const date = new Date(tokenExpiry * 1000); // *1000 to milliseconds
      const cookieExpiry = date.toUTCString();
      document.cookie = `token=${token}; expires=${cookieExpiry}; path=/; secure; samesite=lax`;
    }
  }

  /**
   * Function to get the token from the cookie.
   * @returns token
   * @author Shidan Javaheri + credit ChatGPT + ECSE 428 Group Project
   */
  private getTokenCookie(): string {
    let token = "";
    // get all cookies associated with the document, and trim whitespace
    const cookies = document.cookie.split(";").map((cookie) => cookie.trim());
    // iterate through cookies to find one labelled 'token'
    for (const cookie of cookies) {
      const [cookieName, cookieValue] = cookie.split("=");
      if (cookieName.trim() === "token") {
        token = cookieValue;
        break;
      }
    }
    return token;
  }

  /**
   * Function to create headers with the JWT token
   * @param token
   * @returns HttpHeaders
   * @author Shidan Javaheri + credit ChatGPT
   */
  private createHeaders(token: string): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    });
  }

  /**
   * Method to logout a user by clearing local storage.
   */
  private logout(): void {
    localStorage.removeItem("account");
    // Call the setTokenCookie function from your code or module
    this.setTokenCookie("");
  }

  /**
   * Method to login to the application - creates a cookie for a token if it is valid
   * @param username
   * @param password
   * @returns true if successful, false if not
   * @author Shidan Javaheri + credit ChatGPT + ECSE 428 Group Project
   */
  async login(username: string, password: string): Promise<boolean> {
    // encode the username and password to send the request
    const encodedCreds = Buffer.from(`${username}:${password}`).toString(
      "base64"
    );

    try {
      const res = await firstValueFrom(
        this.http.post(`${this.baseUrl}/login`, null, {
          headers: { Authorization: `Basic ${encodedCreds}` },
          responseType: "text",
        })
      );

      // Check if the response is successful
      if (res) {
        const token = res;
        // Store token in local storage
        this.setTokenCookie(token);
        return true;
      } else {
        console.error("Unable to get token. Empty response.");
        return false;
      }
      // log error if incorrect
    } catch (err) {
      console.error("Something went wrong. Unable to get token.", err);
      return false;
    }
  }

  /**
   * Method to verify the validity of a token.
   * @returns accountDTO if valid, nothing otherwise
   */
  async verifyToken(): Promise<boolean> {
    localStorage.removeItem("account");
    const token = this.getTokenCookie();
    // Check if there is a token
    if (!token) return false;

    // send a get request to the login endpoint
    const res = await firstValueFrom(this.basicGet("/login"));

    if (!res) {
      this.logout();
      if (window.location.pathname !== "/login") {
        window.location.href = "/login";
      }
      return false;
    }
    const accountDTO = JSON.parse(res.body);
    // Problem with the request
    if (!accountDTO) return false;
    // Populate local storage with user info
    // Use JSON.parse() to convert string into object in other files
    localStorage.setItem("account", JSON.stringify(accountDTO));
    return true;
  }
}
