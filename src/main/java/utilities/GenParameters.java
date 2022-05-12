package utilities;

import java.util.Random;

public class GenParameters {

	public static final String RANDOM_STRING_SEEDS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String RANDOM_NUMBER_SEEDS = "0123456789";

	public static String randomName(int length) {
		return randomString(RANDOM_STRING_SEEDS, length);
	}

	public static String randomNameInChi(int xingLength, int mingLength) {
		final String xing = "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张欧阳太史端木上官司马东方独孤南宫万俟闻人夏侯诸葛尉迟";
		final String ming = "秋白南风醉山初彤凝海紫文凌晴香卉雅琴傲安傲之初蝶寻桃代芹诗霜春柏绿夏碧灵";
		String lastname = randomString(xing, xingLength);
		String firstname = randomString(ming, mingLength);
		String chiName = lastname + firstname;
		return chiName;
	}

	public static String randomString(String seeds, int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(seeds.charAt(random.nextInt(seeds.length())));
		}
		return sb.toString();
	}

	public static String randomText(int length) {
		String randomText = "";
		randomText += randomString(RANDOM_STRING_SEEDS + RANDOM_NUMBER_SEEDS, length);
		return randomText;
	}

	public static String randomFreeText(int min, int max) {
		String freeText = "";
		int length = randomMoneyRel(min, max);
		freeText = randomString(RANDOM_STRING_SEEDS + RANDOM_NUMBER_SEEDS, length);
		return freeText;
	}

	public static String randomEmail() {
		int length = 5;
		final String name = RANDOM_STRING_SEEDS;
		final String firstDomain = "0123456789abcdefghijklmnopqrstuvwxyz";
		final String secondDomain = "abcdefghijklmnopqrstuvwxyz";
		String email = new String();
		email += randomString(name, length);
		email += "@" + randomString(firstDomain, length - 2);
		email += "." + randomString(secondDomain, length - 3);
		return email;
	}

	public static String randomPhoneNumber(int length) {
		String phoneNumber = "";
		String pref = "88";
		phoneNumber = pref + randomString(RANDOM_NUMBER_SEEDS, length - pref.length());
		return phoneNumber;
	}

	public static int randomNumber(int maxValue) {
		Random random = new Random();
		return random.nextInt(maxValue);
	}

	public static int randomNumber(int minValue, int maxValue) {
		Random random = new Random();
		return random.nextInt(maxValue) % (maxValue - minValue) + minValue;
	}

	public static int randomMoneyRel(int min, int max) {
		Random random = new Random();
		int money = random.nextInt(max) % (max - min) + min;
		return money;
	}

	public static String randomNo(int length) {
		String numberStr = randomString(RANDOM_NUMBER_SEEDS, length);
		return numberStr;
	}

		
}