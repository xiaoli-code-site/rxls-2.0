import { Category } from "@/api/service/category/type";

/**
 * 商品类型递归查找处理
 * @param category 需要查找的商品分类
 * @param currentPath 当前路径（用于追踪层级）
 * @returns 返回从顶层到目标分类的路径数组
 */
const findTopLevelCategoryFromTree = (
  categoryToFind: Category,
  categories: Category[],
  currentPath: Category[] = []
): Category[] | null => {
  for (let cat of categories) {
    // 将当前分类加入路径
    const path = [...currentPath, cat];
    // 如果找到了目标分类，返回路径
    if (cat.categoryId === categoryToFind.categoryId) {
      return path;
    }
    // 如果当前分类有子分类，则递归查找
    if (cat.category) {
      const result = findTopLevelCategoryFromTree(
        categoryToFind,
        cat.category,
        path
      );
      if (result) {
        return result;
      }
    }
  }

  return null;
};

/**
 * 根据父级分类id 返回旗下所有最底层分类的id
 * @param id 分类id
 * @param categories 分类总数据
 * @returns 返回从底层到目标分类的数组
 */
const findBottomLevelCategoryFromTree = (
  id: number,
  categories: Category[]
): Category[] => {
  const resultList: Category[] = [];

  const recursiveFind = (categoryList: Category[], targetId: number) => {
    for (let cat of categoryList) {
      if (cat.categoryId === targetId) {
        // 找到目标分类后，收集其所有最底层子分类的ID
        collectBottomLevelIds(cat, resultList);
        return true;
      }

      if (cat.category) {
        const found = recursiveFind(cat.category, targetId);
        if (found) {
          return true;
        }
      }
    }
    return false;
  };

  const collectBottomLevelIds = (
    category: Category,
    categories: Category[]
  ) => {
    if (!category.category || category.category.length === 0) {
      // 如果当前分类没有子分类，则它是最底层分类
      categories.push(category);
    } else {
      // 否则，递归收集其所有子分类中最底层分类的ID
      for (let subCat of category.category) {
        collectBottomLevelIds(subCat, categories);
      }
    }
  };

  // 开始递归查找
  recursiveFind(categories, id);

  return resultList;
};

export { findTopLevelCategoryFromTree, findBottomLevelCategoryFromTree };
