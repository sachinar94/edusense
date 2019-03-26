import { NgModule } from '@angular/core';

import { EdusenseSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [EdusenseSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [EdusenseSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class EdusenseSharedCommonModule {}
