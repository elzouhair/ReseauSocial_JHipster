import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Eleve } from 'app/shared/model/eleve.model';
import { EleveService } from './eleve.service';
import { EleveComponent } from './eleve.component';
import { EleveDetailComponent } from './eleve-detail.component';
import { EleveUpdateComponent } from './eleve-update.component';
import { EleveDeletePopupComponent } from './eleve-delete-dialog.component';
import { IEleve } from 'app/shared/model/eleve.model';

@Injectable({ providedIn: 'root' })
export class EleveResolve implements Resolve<IEleve> {
  constructor(private service: EleveService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEleve> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Eleve>) => response.ok),
        map((eleve: HttpResponse<Eleve>) => eleve.body)
      );
    }
    return of(new Eleve());
  }
}

export const eleveRoute: Routes = [
  {
    path: '',
    component: EleveComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Eleves'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EleveDetailComponent,
    resolve: {
      eleve: EleveResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Eleves'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EleveUpdateComponent,
    resolve: {
      eleve: EleveResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Eleves'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EleveUpdateComponent,
    resolve: {
      eleve: EleveResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Eleves'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const elevePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EleveDeletePopupComponent,
    resolve: {
      eleve: EleveResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Eleves'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
