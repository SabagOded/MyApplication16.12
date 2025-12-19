# מפת זרימה והקשרים בפרויקט – REST Countries App

המטרה של המסמך הזה היא לעשות לך **סדר מחשבתי**:
מה קורה, מתי קורה, מי אחראי על מה, ואיך כל המחלקות מדברות אחת עם השנייה.

אם תקרא את זה לאט, אתה אמור להגיע למצב שאתה *יכול להסביר את הפרויקט בעצמך*.

---

## 1. תמונת־על: מי השחקנים בפרויקט?

יש לנו 6 שחקנים מרכזיים:

1. **WelcomeActivity** – מסך פתיחה
2. **MainActivity** – המסך הראשי עם החיפוש והרשימה
3. **RecyclerView** – מצייר רשימה על המסך
4. **CountryAdapter** – מחבר בין נתונים לתצוגה
5. **Country / Name / Flags** – מודלים (Data)
6. **CountryApiService + Retrofit** – מביאים נתונים מהאינטרנט

חשוב:

> אף מחלקה לא עושה הכול. כל אחת אחראית רק על תפקיד אחד.

---

## 2. נקודת ההתחלה – פתיחת האפליקציה

### מה קורה כשמריצים את האפליקציה?

1. מערכת Android פותחת את **WelcomeActivity**

   * למה? כי ב־AndroidManifest היא מוגדרת כ־LAUNCHER

2. WelcomeActivity:

   * מציגה מסך פתיחה
   * מחכה ללחיצה על כפתור

3. לחיצה על הכפתור:

   * יוצרת `Intent`
   * מעבירה למסך הבא: **MainActivity**

⚠️ כאן WelcomeActivity *לא* יודעת כלום על מדינות, API או רשימות.
היא רק שער כניסה.

---

## 3. MainActivity – מנהל המסך הראשי

### האחריות של MainActivity:

MainActivity **לא מציירת מדינות בעצמה**.
היא:

1. טוענת את ה־layout (`activity_main.xml`)
2. מחברת רכיבי UI:

   * SearchView
   * RecyclerView
3. יוצרת Adapter
4. מביאה נתונים מה־API
5. מחברת חיפוש → סינון

כלומר:

> MainActivity = מנהלת תזמורת, לא נגן

---

## 4. שלב הבאת הנתונים – Retrofit ו־API

### מי אחראי על האינטרנט?

❌ לא MainActivity
❌ לא Adapter

✅ **Retrofit + CountryApiService**

### הזרימה:

1. MainActivity מבקשת נתונים:

   * `fetchCountries()`

2. Retrofit:

   * בונה בקשת HTTP
   * שולח ל־[https://restcountries.com](https://restcountries.com)

3. CountryApiService:

   * מגדיר *מה* מבקשים (GET all?fields=...)

4. התשובה חוזרת:

   * JSON מהשרת

5. Gson:

   * ממיר JSON → אובייקטים של Java
   * `Country`, `Name`, `Flags`

6. MainActivity מקבלת:

   * `List<Country>`

⚠️ חשוב:
MainActivity **לא מפרשת JSON** בעצמה.

---

## 5. מודלים – Country, Name, Flags

### מה זה מודל?

מודל = מחלקה שמייצגת *מידע בלבד*.

לדוגמה:

* `Country` – מדינה
* `Name` – שמות המדינה
* `Flags` – קישורים לדגלים

### מה הם *לא* עושים?

❌ לא מציירים
❌ לא יודעים UI
❌ לא עושים חישובים

רק:

> מחזיקים נתונים בצורה מסודרת

---

## 6. RecyclerView – מי מצייר בפועל?

RecyclerView הוא רכיב UI מיוחד:

* הוא **לא יודע** מה זה מדינה
* הוא **לא יודע** מה זה חיפוש
* הוא רק שואל:

> "כמה פריטים יש?"
> "איך נראה פריט מספר X?"

והתשובות מגיעות מה־Adapter.

---

## 7. CountryAdapter – הלב של המסך

### למה Adapter כל כך חשוב?

ה־Adapter הוא החוליה המקשרת בין:

* נתונים (`Country`)
* לתצוגה (`item_country.xml`)

### מה יש בתוך CountryAdapter?

1. `allCountries`

   * כל המדינות מה־API
   * אף פעם לא משתנה בסינון

2. `countries`

   * המדינות שמוצגות כרגע
   * משתנה לפי החיפוש

3. ViewHolder

   * מחזיק הפניות ל־TextView / ImageView

4. onBindViewHolder

   * מקבל Country
   * שם ערכים בטקסטים ובתמונה

---

## 8. חיפוש – איך הכול מתחבר?

### מי מקשיב להקלדה?

* **SearchView**

### מי מקבל את הטקסט?

* MainActivity

### מי מסנן בפועל?

* CountryAdapter

### הזרימה:

1. משתמש מקליד
2. SearchView קורא:

   * `onQueryTextChange`
3. MainActivity:

   * מעבירה את הטקסט ל־`adapter.filter(text)`
4. Adapter:

   * מסנן את הרשימה
   * קורא `notifyDataSetChanged()`
5. RecyclerView:

   * מצייר מחדש

⚠️ MainActivity **לא מסננת בעצמה**.

---

## 9. למה זה בנוי ככה? (חשיבה של מתכנת)

אם מחר תרצה:

* להחליף API
* להחליף עיצוב
* להוסיף מסך פרטים

לא תצטרך לגעת בהכול.

כל חלק מבודד:

* UI ≠ נתונים
* רשת ≠ תצוגה
* חיפוש ≠ ציור

זו בדיוק הסיבה שמלמדים RecyclerView ו־Adapter.

---

## 10. משפט מפתח לזיכרון

> MainActivity מחליטה *מה* קורה
> Adapter מחליט *מה* מוצג
> RecyclerView מצייר *איך* זה נראה

---

אם תרצה, בצעד הבא אנחנו יכולים:

* לצייר תרשים זרימה ויזואלי
* או לעבור מחלקה־מחלקה ולשאול: "אם אני מוחק אותה – מה נשבר?"

תגיד איך בא לך להעמיק 👌
