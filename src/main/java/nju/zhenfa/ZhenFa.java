package nju.zhenfa;

import nju.Field;
import nju.creature.*;

import java.util.ArrayList;

public interface ZhenFa {
    void apply(Field field, ArrayList<? extends Creature> creatures);
}