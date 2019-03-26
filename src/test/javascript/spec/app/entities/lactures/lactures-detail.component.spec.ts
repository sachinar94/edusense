/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdusenseTestModule } from '../../../test.module';
import { LacturesDetailComponent } from 'app/entities/lactures/lactures-detail.component';
import { Lactures } from 'app/shared/model/lactures.model';

describe('Component Tests', () => {
    describe('Lactures Management Detail Component', () => {
        let comp: LacturesDetailComponent;
        let fixture: ComponentFixture<LacturesDetailComponent>;
        const route = ({ data: of({ lactures: new Lactures(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdusenseTestModule],
                declarations: [LacturesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LacturesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LacturesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lactures).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
