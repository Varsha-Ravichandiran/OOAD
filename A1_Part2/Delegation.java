
 class Delegation {
	
	public static void main(String args[]) {
		B b = new B();
		System.out.println(b.f()+b.g()-b.p(1)+b.q(2));
		
		B2 b2 = new B2();
		System.out.println(b2.f()+b2.g()-b2.p(1)+b2.q(2));
		
		D d = new D();
		System.out.println(d.f()+d.g()-d.h()+d.p(1)-d.q(2)+d.r());
		
		D2 d2 = new D2();
		System.out.println(d2.f()+d2.g()-d2.h()+d2.p(1)-d2.q(2)+d2.r());	
		
	}
}
 
 class Delegation2 {
		
	public static void main(String args[]) {
		
		E e = new E();
		System.out.println(e.f()-e.g()+e.h()-e.p(1)+e.q(2)-e.r()+e.k(100));
		
		E2 e2 = new E2();
		System.out.println(e2.f()-e2.g()+e2.h()-e2.p(1)+e2.q(2)-e2.r()+e2.k(100));

		F f = new F();
		System.out.println(f.f()+f.g()+f.h()+f.p(1)-f.q(2)-f.r()-f.j(10)+f.l(100));
		
		F2 f2 = new F2();
		System.out.println(f2.f()+f2.g()+f2.h()+f2.p(1)-f2.q(2)-f2.r()-f2.j(10)+f2.l(100));	
	}
}
 
 
abstract class A {
	int a1 = 100;
	int a2 = 200;

	public int f() {
		return a1 + p(100) + q(100);
	}

	protected abstract int p(int m);

	protected abstract int q(int m);
}

class B extends A {
	int b1 = 1000;
	int b2 = 2000;

	public int g() {
		return this.p(100) + this.q(200); 
	}

	public int p(int m) {
		return m + a1+b1;
	}

	public int q(int m) {
		return m + a2+b2;
	}
}

abstract class C extends B {
	int c1 = 10000;
	int c2 = 20000;

	public int r() {
		return f() + g() + h();
	}

	public int q(int m) {
		return m + a2 + b2 + c2;
	}

	protected abstract int h();
}

class D extends C {
	int d1 = 1000000;
	int d2 = 2000000;

	public int r() {
		return f() + g() + h();
	}

	public int p(int m) {
		return super.p(m) + d2;
	}

	public int h() {
		return a1 + b1 + c1;
	}
	
	public int j(int n) {
		return r() + super.r();
	}
}

class E extends C {
	int e1 = 1;
	int e2 = 2;

	public int q(int m) {
		return p(m) + c2;
	}

	public int h() {
		return a1 + b1 + e1;
	}
	
	public int k(int n) {
		return q(n) + super.q(n);
	}

}

class F extends D {
	int f1 = 10;
	int f2 = 20;

	public int q(int m) {
		return p(m) + c1 + d1;
	}

	public int h() {
		return c2 + f2;
	}
	
	public int l(int n) {
		return q(n) + super.q(n);
	}
}


// ===== TRANSFORMATION IN TERMS OF DELEGATION ======


// INTERFACES

interface IA {
	public int f();
}

interface IB extends IA {
	public int g();
	public int p(int m);
	public int q(int m);
}

interface IC extends IB {
	public int r();
	public int q(int m);
	public int h();
}

interface ID extends IC {
	public int r();
	public int p(int m);
	public int h();
	public int j(int n);
}

interface IE extends IC {
	public int q(int m);
	public int h();
	public int k(int n);
}

interface IF extends ID {
	public int q(int m);
	public int h();
	public int l(int n);
}

// CLASSES 

class A2 implements IA {
	int a1 = 100;
	int a2 = 200;
	IB sub;
	
	public A2(IB I)
	{
		sub=I;
	}
	
	public int f() {
		return a1 + sub.p(100) + sub.q(100);
	}
}

class B2 implements IB {
	int b1 = 1000;
	int b2 = 2000;
	A2 super2;
	IB this2;
	
	public B2()
	{
		super2 = new A2(this);
		this2= this;
	}
	
	public B2(IB I)
	{
		this2 =I;
		super2= new A2(I);
	}
	
	public int g() {
		return this2.p(100) + this2.q(200); 
	}

	public int p(int m) {
		return m + super2.a1+b1;
	}

	public int q(int m) {
		return m + super2.a2+b2;
	}

	@Override
	public int f() {
		// TODO Auto-generated method stub
		return super2.f();
	}
}

class C2 implements IC {
	int c1 = 10000;
	int c2 = 20000;
    B2 super2;
    IC sub;
    
    public C2(IC I)
    {   
    	sub = I;
    	super2 = new B2(I);
    }
    
	public int r() {
		return f() + g() + h();
	}

	public int q(int m) {
		return m + super2.super2.a2 + super2.b2 + c2;
	}
	
	@Override
	public int g() {
		// TODO Auto-generated method stub
		return super2.g();
	}
	
	@Override
	public int p(int m) {
		// TODO Auto-generated method stub
		return super2.p(m);
	}
	
	@Override
	public int f() {
		// TODO Auto-generated method stub
		return super2.f();
	}
	
	@Override
	public int h() {
		// TODO Auto-generated method stub
		return sub.h();
	}	
}

class D2 implements ID {
	int d1 = 1000000;
	int d2 = 2000000;
	C2 super2;
	ID this2;
	
	public D2()
	{
		super2 = new C2(this);
		this2 = this;
	}
	
	public D2(ID I)
	{
		this2 = I;
		super2 = new C2(I);
	}
	
	public int r() {
		return this2.f() + this2.g() + this2.h();
	}

	public int p(int m) {
		return super2.p(m) + d2;
	}
	
	public int h() {
		return super2.super2.super2.a1 + super2.super2.b1 + super2.c1;
	}
	
	public int j(int n) {
		return r() + super2.r();
	}

	@Override
	public int q(int m) {
		// TODO Auto-generated method stub
		return super2.q(m);
	}

	@Override
	public int g() {
		// TODO Auto-generated method stub
		return super2.g();
	}

	@Override
	public int f() {
		// TODO Auto-generated method stub
		return super2.f();
	}
}

class E2 implements IE {
	int e1 = 1;
	int e2 = 2;
	C2 super2;
	
	public E2()
	{
		super2= new C2(this);
	}
	
	public int q(int m) {
		return p(m) + super2.c2;
	}

	public int h() {
		return super2.super2.super2.a1 + super2.super2.b1 + e1;
	}
	
	public int k(int n) {
		return q(n) + super2.q(n);
	}

	@Override
	public int r() {
		// TODO Auto-generated method stub
		return super2.r();
	}

	@Override
	public int g() {
		// TODO Auto-generated method stub
		return super2.g();
	}

	@Override
	public int p(int m) {
		// TODO Auto-generated method stub
		return super2.p(m);
	}

	@Override
	public int f() {
		// TODO Auto-generated method stub
		return super2.f();
	}
}

class F2 implements IF {
	int f1 = 10;
	int f2 = 20;
	D2 super2;
	
	public F2()
	{
		super2 = new D2(this);
	}

	public int q(int m) {
		return p(m) + super2.super2.c1 + super2.d1;
	}

	public int h() {
		return super2.super2.c2 + f2;
	}
	
	public int l(int n) {
		return q(n) + super2.super2.q(n);
	}

	@Override
	public int p(int m) {
		// TODO Auto-generated method stub
		return super2.p(m);
	}

	@Override
	public int j(int n) {
		// TODO Auto-generated method stub
		return super2.j(n);
	}

	@Override
	public int r() {
		// TODO Auto-generated method stub
		return super2.r();
	}

	@Override
	public int g() {
		// TODO Auto-generated method stub
		return super2.g();
	}

	@Override
	public int f() {
		// TODO Auto-generated method stub
		return super2.f();
	}
}