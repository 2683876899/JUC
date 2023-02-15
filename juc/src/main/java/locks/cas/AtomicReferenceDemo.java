package locks.cas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@Getter
@ToString

class User{
    private Integer age;
    private String name;
}

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<>();
        User z3 = new User(14,"z3");
        User li4 = new User(28,"li4");
        atomicReference.set(z3);
        System.out.println(atomicReference.compareAndSet(z3,li4) + "\t"+atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3,li4) + "\t"+atomicReference.get().toString());
    }
}
