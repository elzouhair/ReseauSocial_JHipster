import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEleve } from 'app/shared/model/eleve.model';

type EntityResponseType = HttpResponse<IEleve>;
type EntityArrayResponseType = HttpResponse<IEleve[]>;

@Injectable({ providedIn: 'root' })
export class EleveService {
  public resourceUrl = SERVER_API_URL + 'api/eleves';

  constructor(protected http: HttpClient) {}

  create(eleve: IEleve): Observable<EntityResponseType> {
    return this.http.post<IEleve>(this.resourceUrl, eleve, { observe: 'response' });
  }

  update(eleve: IEleve): Observable<EntityResponseType> {
    return this.http.put<IEleve>(this.resourceUrl, eleve, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEleve>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEleve[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
