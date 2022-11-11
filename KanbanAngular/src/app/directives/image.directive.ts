import { Directive, ElementRef, HostListener, Input, Renderer2 } from '@angular/core';

@Directive({
  standalone:true,
  selector: '[appImage]'
})
export class ImageDirective {
  @Input('appImage') color:any="";
  constructor(private elementRef:ElementRef, private renderer:Renderer2) { }

  // @HostListener('document:click', ['$event'])
  // DocumentClick(event: Event) {
  //   if (this.elementRef.nativeElement.contains(event.target))
  //   this.renderer.setStyle(this.elementRef.nativeElement,'border',"3px groove rgb(93, 93, 249)");
  //   else
  //   this.renderer.setStyle(this.elementRef.nativeElement,'border',null);
      
  // }

  @HostListener('document:click', ['$event'])
  onClick(event: Event) {
    console.log(event);
    if(this.elementRef.nativeElement.contains(event.target))
    this.renderer.setStyle(this.elementRef.nativeElement,'border',"3px groove #897622");
    else
    this.renderer.setStyle(this.elementRef.nativeElement,'border',null);
  }

}
