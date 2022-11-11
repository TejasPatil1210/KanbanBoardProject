import { Directive, ElementRef, HostListener, Renderer2 } from '@angular/core';

@Directive({
  standalone:true,
  selector: '[appUnderlineForProjects]'
})
export class UnderlineForProjectsDirective {

 
  constructor(private elementRef:ElementRef, private renderer:Renderer2) { }

  @HostListener('mouseenter') onMouseEnter()
  {
    this.renderer.setStyle(this.elementRef.nativeElement,'border-bottom',"2px solid black");
  }
  @HostListener('mouseleave') onMouseLeave()
  {
    this.renderer.setStyle(this.elementRef.nativeElement,'border-bottom',null);
  }

}
