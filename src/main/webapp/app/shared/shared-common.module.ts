import { NgModule } from '@angular/core';

import { ReseausocialjhSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [ReseausocialjhSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [ReseausocialjhSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ReseausocialjhSharedCommonModule {}
