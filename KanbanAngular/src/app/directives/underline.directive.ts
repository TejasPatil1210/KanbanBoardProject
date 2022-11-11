import { Directive, ElementRef, HostListener, Renderer2 } from '@angular/core';

@Directive({
  standalone:true,
  selector: '[appUnderline]'
})
export class UnderlineDirective {

  constructor(private elementRef:ElementRef, private renderer:Renderer2) { }
  
  
  // @HostListener('document:click', ['$event'])
  @HostListener('mouseenter') onMouseEnter()
  {
    this.renderer.setStyle(this.elementRef.nativeElement,'border-bottom',"3px solid black");
  }
  @HostListener('mouseleave') onMouseLeave()
  {
    this.renderer.setStyle(this.elementRef.nativeElement,'border-bottom',null);
  }

}
