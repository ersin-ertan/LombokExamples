package com.nullcognition.lombokexamples;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Synchronized;
import lombok.ToString;
import lombok.Value;


public class MainActivity extends ActionBarActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState){
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
   }


   @Override
   public boolean onCreateOptionsMenu(Menu menu){
	  // Inflate the menu; this adds items to the action bar if it is present.
	  getMenuInflater().inflate(R.menu.menu_main, menu);
	  return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item){
	  // Handle action bar item clicks here. The action bar will
	  // automatically handle clicks on the Home/Up button, so long
	  // as you specify a parent activity in AndroidManifest.xml.
	  int id = item.getItemId();

	  //noinspection SimplifiableIfStatement
	  if(id == R.id.action_settings){
		 return true;
	  }

	  return super.onOptionsItemSelected(item);
   }
}


@EqualsAndHashCode // may use callSuper to check supers equality, may 'exclude' and 'of' fields like toString
@ToString(callSuper = true, exclude = "isBool")
  // will return toString of all fields and the super, in one string, of = "anInt" is inclusive toString-ing
class LombokClass {

   LombokClass(){}

   @NonNull // ensures that the input or output from the get or set is not null else a NullPointerException is thrown
   // may be called on constructors, parameters, fields and methods(not sure about this last one)
   @Getter
   @Setter
   private boolean isBool = true;

   @Getter(AccessLevel.PACKAGE)
   @Setter(AccessLevel.PRIVATE)
   int anInt = 0;

}

// Setting the value of the staticConstructor parameter to the desired method name will cause Lombok to make the generated constructor private and
// expose a a static factory method of the given name.
@Data(staticConstructor = "myStaticCon")
class AtData {
   // Does all above and triggers Lombok's constructor generation. This adds a public constructor that takes any @NonNull or final fields as parameters.
}

// there is a @Cleanup annotation, that auto closes the given opened resource, but java 7 has try-catch with resources


class Sync {
   // The synchronized keyword will lock on the current object (this) in the case of an instance method or on the class object for a static method.
   // lock explicitly on a separate object that is dedicated solely to that purpose and not exposed in such a way as to allow unsolicited locking


   private int value = 3;

   @Synchronized
   public int updateValue(int inValue){

	  // val example = new ArrayList<String>(); // val is not working, returns a final modifier and interpreted type; much the opposite side of a <> diamond(w.o. final)

	  value += inValue;

	  return value;
   }
}

// @SneakyThrows // don't quite understand why you would need this...

// @NoArgsConstructor, @RequiredArgsConstructor, @AllArgsConstructor
// Constructors made to order: Generates constructors that take no arguments, one argument per final / non-null field, or one argument for every field.

// this will be useful in dagger 2 for the constructors
/*
Each of these annotations allows an alternate form, where the generated constructor is always private, and an additional static factory method that wraps around the private constructor is generated. This mode is enabled by supplying the staticName value for the annotation, like so: @RequiredArgsConstructor(staticName="of"). Such a static factory method will infer generics, unlike a normal constructor. This means your API users get write MapEntry.of("foo", 5) instead of the much longer new MapEntry<String, Integer>("foo", 5).

  To put annotations on the generated constructor, you can use onConstructor=@__({@AnnotationsHere}), but be careful; this is an experimental feature. For more details see the documentation on the onX feature.

  Static fields are skipped by these annotations.
*/

// @Value is the immutable variant of @Data; all fields are made private and final by default, and setters are not generated. The class itself is also
// made final by default, because immutability is not something that can be forced onto a subclass.

@Value
class Val {

   int thisisfinal = 3;
   // will generate a constructor
}

class GetterLazyExample {

   //calculated once forever, thread safe, use a getter to access the value
   @Getter(lazy = true)
   private final double[] cached = expensive();

   private double[] expensive(){
	  double[] result = new double[1000000];
	  for(int i = 0; i < result.length; i++){
		 result[i] = Math.asin(i);
	  }
	  return result;
   }
}

@Builder (builderClassName = "HelloWorldBuilder", buildMethodName = "execute", builderMethodName = "helloWorld")
// does not work with other constructor or method generating annotations
class BuilderLom { // multi calls of a List is put in one, @Singular does something with collections...

   // produces a complex builder api, an object which can be built with multiple chaining(sets), at anytime. Can be placed on class, constructor or static method

   /*
   What it does is:
	An inner static class named FooBuilder, with the same type arguments as the static method (called the builder).
	In the builder: One private non-static non-final field for each parameter of the target.
	In the builder: A package private no-args empty constructor.
	In the builder: A 'setter'-like method for each parameter of the target: It has the same type as that parameter and the same name. It returns the builder itself, so that the setter calls can be chained, as in the above example.
	In the builder: A build() method which calls the static method, passing in each field. It returns the same type that the target returns.
	In the builder: A sensible toString() implementation.
	In the class containing the target: A builder() method, which creates a new instance of the builder.*/
}








































