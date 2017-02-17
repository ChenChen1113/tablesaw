package com.github.lwhite1.tablesaw.mapping;

import com.github.lwhite1.tablesaw.api.BooleanColumn;
import com.github.lwhite1.tablesaw.columns.Column;

/**
 * An interface for mapping operations unique to Boolean columns
 */
public interface BooleanMapUtils extends Column {

  /*
   * TODO(lwhite): Replace this implementation with a roaring bitmap version
   */
  default BooleanColumn and(BooleanColumn... columns) {
    BooleanColumn newColumn = BooleanColumn.create("");
    BooleanColumn thisColumn = (BooleanColumn) this;
    for (int i = 0; i < this.size(); i++) {
      boolean booleanValue = thisColumn.get(i);
      if (!booleanValue) {
        newColumn.add(false);
      } else {
        boolean result = true;
        for (BooleanColumn booleanColumn : columns) {
          result = booleanColumn.get(i);
          if (!result) {
            newColumn.add(false);
            break;
          }
        }
        newColumn.add(result);
      }
    }
    return newColumn;
  }

  default BooleanColumn or(BooleanColumn... columns) {
    BooleanColumn newColumn = BooleanColumn.create("");
    BooleanColumn thisColumn = (BooleanColumn) this;

    for (int i = 0; i < this.size(); i++) {
      boolean booleanValue = thisColumn.get(i);
      if (booleanValue) {
        newColumn.add(true);
      } else {
        boolean result = false;
        for (BooleanColumn booleanColumn : columns) {
          result = booleanColumn.get(i);
          if (result) {
            newColumn.add(true);
            break;
          }
        }
        newColumn.add(result);
      }
    }
    return newColumn;
  }
}
