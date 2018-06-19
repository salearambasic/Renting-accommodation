import { AccommodationService } from '../../services/accommodation.service';
import { datepicker, getFormattedDate } from '../../../assets/js/script.js';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { Router } from '@angular/router';
import { CityService } from '../../services/city.service';
import { AdditionalServiceService } from '../../services/additional-service.service';

@Component({
  selector: 'app-accommodation-list',
  templateUrl: './accommodation-list.component.html',
  styleUrls: ['./accommodation-list.component.css'],
  animations: [fadeIn()]
})
export class AccommodationListComponent implements OnInit {

  private accommodations = [];
  private additionalServices = [];
  private advancedOptions: boolean;

  constructor(
    private accommodationService: AccommodationService,
    private additionalServiceService: AdditionalServiceService,
    private cityService: CityService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  ngOnInit() {
    datepicker();
    this.advancedOptions = false;
    this.accommodationService.getAccommodations()
    .subscribe(res => this.accommodations = res);
    this.additionalServiceService.getAdditionalServices()
    .subscribe(res => this.additionalServices = res);
  }

  searchForm = this.formBuilder.group({
    city: ['', Validators.required],
    persons: [0, Validators.compose([
      Validators.required,
      Validators.min(1)
    ])]
  });

  searchApartments() {
    this.cityService.getCityByName(this.searchForm.value['city'])
    .subscribe(res => {
      const queryParams = {
        'city': res['id'],
        'persons': this.searchForm.value['persons'],
        'startDate': document.querySelector('#startDate')['value'],
        'endDate': document.querySelector('#endDate')['value']
      }
      if (this.advancedOptions) {
        queryParams['type'] = document.querySelector('#type')['value'];
        queryParams['category'] = document.querySelector('#category')['value'];
      }
      this.router.navigate(['/accommodations/search'], { queryParams: queryParams });
    }, err => {
      console.log(err);
    });
  }

  toggleOptions() {
    this.advancedOptions = !this.advancedOptions;
    document.querySelector('#toggleOption').textContent = this.advancedOptions ? 'Hide' : 'Show';
  }
}
