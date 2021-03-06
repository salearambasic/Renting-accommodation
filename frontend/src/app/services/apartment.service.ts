import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Apartment } from '../models/Apartment';

@Injectable()
export class ApartmentService {

  private url: string = 'https://localhost:8081/api/apartments/';
  private headers = new HttpHeaders()
  .append('Content-Type', 'application/json')
  .append('Authorization', 'Bearer ' + localStorage.getItem('token'));

  constructor(private http: HttpClient) { }

  getApartments(accommodationId) {
    return this.http.get<Apartment[]>(this.url + 'accommodation/' + accommodationId);
  }

  getApartment(apartmentId) {
    return this.http.get<Apartment>(this.url + apartmentId);
  }

  getApartmentByAccommodationId(accommodationId, apartmentId) {
    return this.http.get<Apartment>(this.url + apartmentId + '/accommodation/' + accommodationId);
  }

  getApartmentsByBasicQueryParams(city, persons, startDate, endDate) {
    return this.http.get<Apartment[]>(this.url + 'search', { params: {
      'city': city,
      'persons': persons,
      'startDate': startDate,
      'endDate': endDate
    }});
  }

  getApartmentsByAdvancedQueryParams(params) {
    return this.http.get<Apartment[]>(this.url + 'advanced-search', { params: params });
  }
}
