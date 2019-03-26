/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EdusenseTestModule } from '../../../test.module';
import { LacturesComponent } from 'app/entities/lactures/lactures.component';
import { LacturesService } from 'app/entities/lactures/lactures.service';
import { Lactures } from 'app/shared/model/lactures.model';

describe('Component Tests', () => {
    describe('Lactures Management Component', () => {
        let comp: LacturesComponent;
        let fixture: ComponentFixture<LacturesComponent>;
        let service: LacturesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdusenseTestModule],
                declarations: [LacturesComponent],
                providers: []
            })
                .overrideTemplate(LacturesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LacturesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LacturesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Lactures(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.lactures[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
